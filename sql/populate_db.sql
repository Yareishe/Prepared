SELECT * FROM worker WHERE name = ?;
INSERT INTO worker (NAME, BIRTHDAY, LEVEL, SALARY)
VALUES
    (?, ?, ?, ?);
INSERT INTO client (ID, NAME)
VALUES
    (?, ?);
INSERT INTO project (ID, CLIENT_ID, START_DATE, FINISH_DATE, PROJECT_NAME)
VALUES (?,?,?,?, ?);
INSERT INTO project_worker (PROJECT_ID, WORKER_ID)
VALUES
    (?, ?);





