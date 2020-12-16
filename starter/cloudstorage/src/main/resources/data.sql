-- insert default record so can develop easier
INSERT INTO USERS( username, salt, password, firstname,lastname) VALUES ('sa','V6cFIR1V3H/4mguXJobkIw==','WGjXOd/uxq9syST23jF/bA==','fname','lname');
INSERT INTO FILES (filename, contenttype, filesize, userid) VALUES ('file1','text','550kb',1);
INSERT INTO NOTES (notetitle, notedescription, userid) VALUES ('note title','note description in here',1);
INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES('http://localhost:8080','sa','WGjXOd/uxq9syST23jF/bA==','sa',1);

INSERT INTO USERS( username, salt, password, firstname,lastname) VALUES ('sa2','V6cFIR1V3H/4mguXJobkIw==','WGjXOd/uxq9syST23jF/bA==','fname2','lname2');
INSERT INTO FILES (filename, contenttype, filesize, userid) VALUES ('file1','text','550kb',2);
INSERT INTO NOTES (notetitle, notedescription, userid) VALUES ('note title','note description in here',2);
INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES('http://localhost:8080','sa2','WGjXOd/uxq9syST23jF/bA==','sa',2);