INSERT INTO Task (id,title,description,completed) VALUES (NEXT VALUE FOR task_id_seq ,'autoTask 1','b desc',0);
INSERT INTO Task (id,title,description,completed) VALUES (NEXT VALUE FOR task_id_seq,'autoTask 2','a desc',0);
INSERT INTO Task (id,title,description,completed) VALUES (NEXT VALUE FOR task_id_seq,'autoTask 3', 'c desc',1);

--ALTER SEQUENCE task_id_seq RESTART WITH 4;