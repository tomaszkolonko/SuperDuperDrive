
INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES('John', 'FeM1S6JvKRjI6ilgI2CT2w==', 'A4XJOBa8w6QbtT14Vy4AjA==', 'John', 'Doe');

INSERT INTO NOTES (noteid, notetitle, notedescription, userid) VALUES('1', 'TestNote', 'Description of TestNote', '1');
INSERT INTO NOTES (noteid, notetitle, notedescription, userid) VALUES('2', 'Journal', 'Today was really nice', '1');

INSERT INTO CREDENTIALS (credentialid, url, userName, key, password, userId) VALUES ('1', 'www.google.ch', 'John', '6yGQKDrIrspo19Jqvr7r7Q==', 'V1saqhCkytx221GBTf+j7g==', '1')
