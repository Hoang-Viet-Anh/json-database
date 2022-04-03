# JSON Database
JetBrains Academy. Project: JSON Database.

JSON database is a single file database that stores information in</br>
the form of JSON. It is a remote database, so it's usually accessed</br>
through the Internet.

In fifth stage, improved your client and server by adding the ability</br>
to work with files.

The server keep the database on the hard drive in the db.json file</br>
and update only after setting a new value or deleting one.

To make request to server you should start jar clientFile with</br>
parameters like below:</br>

-t set -k "Some key" -v "Here is some text to store on the server"

-t is the type of the request, and -k is the key. -v is the value to save</br>
in the database: you only need it in case of a set request.

In last stage, improved database. It is able to store not only</br>
strings, but any JSON objects as values.

The key should not only be a string since the user needs to</br>
retrieve part of the JSON value. For example, in the code</br>
snippet below, the user wants to get only the surname of the</br>
person:

{
... ,

    "person": {
        "name": "Adam",
        "surname": "Smith"
    }
    ...
}

Then, the user should type the full path to this field in a</br>
form of a JSON array: ["person", "surname"]. If the user</br>
wants to get the full person object, then they should type</br>
["person"].

Jar file is located by ./JSON Database/task/build/libs.