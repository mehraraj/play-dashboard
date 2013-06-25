/*
 * Copyright 2012 Stephane Godbillon
 *
 * This sample is in the public domain.
 */
package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current
import play.api.libs.json._
import play.api.libs.iteratee._
import play.modules.reactivemongo._
import play.modules.reactivemongo.PlayBsonImplicits._
import reactivemongo.api._
import reactivemongo.bson.handlers.DefaultBSONHandlers.DefaultBSONReaderHandler
import scala.concurrent.{ExecutionContext, Future}

object Application extends Controller with MongoController {

	
   val futureCollection :Future[Collection] = {
    
    val db = ReactiveMongoPlugin.db
    val collection = db.collection("submission")
    collection.stats().flatMap {
      case stats if !stats.capped =>
        // the collection is not capped, so we convert it
        println("converting to capped")
        collection.convertToCapped(10737418240l, None)
        Future(collection)
      case _ => Future(collection)
   }.recover {
      // the collection does not exist, so we create it
      case _ =>
        println("creating capped collection...")
        //collection.createCapped(1024 * 1024, None)
        collection.create(true);
    }.map { _ =>
      println("the capped collection is available")
      collection
    }
  }
  def index = Action {
    Ok(views.html.index())
    
    
  }

  /**
   * Handling request from Websocket client. Tails MongoDB submission
   * and returns  submission details.
   */
  def dashboard = WebSocket.using[JsValue] { request =>
    // Inserts the received messages into the capped collection
    val in = Iteratee.flatten(futureCollection.map(collection => Iteratee.foreach[JsValue] { json =>
      println("received " + json)
      collection.insert(json)
    }))
    
    // Enumerates the capped collection
    val out = {
      val futureEnumerator = futureCollection.map { collection =>
        // so we are sure that the collection exists and is a capped one
        val cursor = collection.find(
          // we want all the documents
          Json.obj(),
          // the cursor must be tailable and await data
          QueryOpts().tailable.awaitData)
        // ok, let's enumerate it
        cursor.enumerate
      }
      Enumerator.flatten(futureEnumerator)
    }

    // We're done!
    (in,out)
  }
  
   def metric = Action {
    Ok(views.html.metric())
  }
  
 
}