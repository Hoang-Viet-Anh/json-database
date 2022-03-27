# JSON Database
JetBrains Academy. Project: JSON Database.

JSON database is a single file database that stores information in</br>
the form of JSON. It is a remote database, so it's usually accessed</br>
through the Internet.

In first stage simulated a database that can store text information in</br>
an array of the size 100.From the start of the database, every cell</br>
contains an empty string. Users can save strings in the cells, read</br>
the information from these cells, and delete that information if</br>
needed. After a string has been deleted, that cell should contain an</br>
empty string.

The user can use the <b>set</b>, <b>get</b>, or, <b>delete</b> commands.

After <b>set</b>, the user should specify a number (1-100) and the text to</br>
be saved in the cell. If the index is wrong, the program should output</br>
ERROR, otherwise, output OK. If the specified cell already contains</br>
information, it should be overwritten.

After <b>get</b>, the user should specify the number of the cell from which</br>
they want to get information. If the cell is empty or the index is wrong,</br>
the program should output ERROR; otherwise, the program should</br>
output the content of the cell.

After <b>delete</b>, the user should specify the number of the cell. If the</br>
index is wrong, the program should output ERROR; otherwise,</br>
output OK. If the string is empty, you don't have to do anything.

To <b>exit</b> the program, the user should enter exit.

Jar file is located by ./JSON Database/task/build/libs.