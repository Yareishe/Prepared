SELECT c.NAME, COUNT(c.ID) AS PROJECT_COUNT
FROM client c
         JOIN project p ON c.ID = p.CLIENT_ID
GROUP BY c.NAME
HAVING COUNT(p.ID) = (
    SELECT MAX(project_count)
    FROM (
             SELECT COUNT(p.ID) AS project_count
             FROM client c
                      JOIN project p ON c.ID = p.CLIENT_ID
             GROUP BY c.NAME
         ) AS counts
);