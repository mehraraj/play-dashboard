Scala Play2 Dashboard
======================

A simple dashboard built on the top of MongoDB and using Scala Play2 framework. It demonstrates how to use ReactiveMongo Driver to tail a mongodb collection and display the result in a dashboard.

You will need have an instance of MongoDB running - Modify the details of the database in conf/application.conf.

Here is a sample of data needed to be uploaded into a database.

{
	"_id" : "1910111.1038/archiva-69-xyzn609",
	"_class" : "com.nature.archiva.service.model.SubmissionRecord",
	"doi" : "1910111.1038/archiva-69-xyzn609",
	"fileName" : "archiva-69.xml",
	"zipFile" : "Test-9-valid-1-invalid.zip",
	"journalID" : "xyzn",
	"date" : ISODate("2013-06-25T07:56:07.167Z"),
	"report" : {
		"message" : [
			{
				"_id" : null,
				"content" : "VALIDATION SUCCESSFUL"
			}
		],
		"href" : "archiva-69.xml",
		"status" : "SUCCESS"
	},
	"ingestion" : {
		"host" : "192.168.88.103",
		"path" : {
			"value" : "/dev-ingest/mnt/fs/Web/NPG/xyzn/journal/v3001/n3001/xml/archiva-69.xml",
			"derivedFromMl" : false
		},
		"database" : "NLMDocuments",
		"collections" : "xyzn,journals_xyzn,journals_nature",
		"doi" : "1910111.1038/archiva-69-xyzn609",
		"volume" : "v3001",
		"issue" : "n3001",
		"status" : "SUCCESS",
		"filename" : "archiva-69.xml"
	}
}

# To run the application

just run: play run and you can access the application on localhost:9000.

