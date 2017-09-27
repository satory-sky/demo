===========================================================
       ms-qantas-crawler
===========================================================

This service will get a tree of of available child pages linked to the URL (like a simple web crawler). Each tree node should have at least following fields: url, title and nodes (details provided in Response Examples). It provides deep-crawling solution through multiple levels in link hierarchy that can be restricted by tree depth. Application expects base URL and tree depth as a parameter representing by Json. 

### Follow below steps to run the service: ###
1. Maven, Java and Tomcat should be installed on a machine
2. Go to project dir location (for example: "cd: D:\projects\demo\ms-qantas-crawler") and execute "ant clean install". An artifact "ms-core.war" will be generated in {project_location}\ms-core\target dir.
    2.1 Generated Jacoco report for JUnit test coverage at could be found at {project_location}\target\site\jacoco\index.html 
3. Run and Stop the service:
    3.1 Copy a generated artifact "ms-core.war" to {TOMCAT_HOME}\webapps\ dir  
    3.2 To run service execute {TOMCAT_HOME}\bin\startup.bat or {TOMCAT_HOME}\bin\startup.sh 
    3.3 To stop service execute {TOMCAT_HOME}\bin\shutdown.bat or {TOMCAT_HOME}\bin\shutdown.sh
    
### Assumption ###
* Current implementation doe not support proper tree root url validation. However, if any of link is incorrect, it will be passed, marked as empty and put corresponding information added in log.
* Application parses HTML only. Url links from javascript are not collected in the current version.
* JSoup library is used for HTML parsing. However it is not perfect and does not open some non-standard links. Those links are marked as incorrect.    

### Possible enhancements: ###
* add proper validation for input parameters (tree root url)
* ms-common may be isolated as separated jar library
* increase JUnit code coverage
* add Swagger annotation and file generation
* extend response type (like Result in Play framework)
* use non blocking calls (CompletionStage) + Akka (like Result in Play framework)
* add /_health, /_info and /_diagnostics endpoints for health monitoring
* add proper logging mechanism and send logs for collection and analysis to ELK/Splunk
* add correlation Id and application label for distributed transactions
* caching layer
* extra filtering parameters
* add security filters or put it behind security gateway (like OKAPI)
* integrate application with SonareQube

### For any documentation issue or suggestion please contact: ###
Team Serenity Development <alex.kontarero@gmail.com>

### Requests Examples ###
POST http://localhost:8080/ms-core/v1/crawler/tree
```
{
    "baseUrl" : "https://trello.com/",
    "searchDepth" : 2
}
```
### Response Examples ###
```
[  
   {  
      "url":"http://goole.com",
      "tite":"Google",
      "nodes":[  
         {  
            "url":"http://goole.com/page1",
            "tite":"Google Page 1",
            "nodes":[  
               {  
                  "url":"http://some-site.com/",
                  "tite":"Some Site 1",
                  "nodes":[
                  ]
               }
            ]
         },
         {  
            "url":"http://goole.com/page1",
            "tite":"Google Page 1",
            "nodes":[
            ]
         }
      ]
   }
]
```