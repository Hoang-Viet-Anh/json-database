# JSON Database
JetBrains Academy. Project: JSON Database.

JSON database is a single file database that stores information in</br>
the form of JSON. It is a remote database, so it's usually accessed</br>
through the Internet.

In fourth stage, the database stored in the JSON format.</br>
Also, you should send to the server a valid JSON (as a string) which</br>
includes all the parameters needed to execute the request.

To make request to server you should start jar clientFile with</br>
parameters like below:</br>

-t set -k "Some key" -v "Here is some text to store on the server"

-t is the type of the request, and -k is the key. -v is the value to save</br>
in the database: you only need it in case of a set request.


Jar file is located by ./JSON Database/task/build/libs.