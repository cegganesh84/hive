set hive.optimize.ppd=true;

EXPLAIN EXTENDED
FROM (
  FROM srcpart src
  SELECT TRANSFORM(src.ds, src.key, src.value)
         USING 'cat' AS (ds, tkey, tvalue)
  WHERE src.ds = '2008-04-08' 
  CLUSTER BY tkey 
) tmap
SELECT tmap.tkey, tmap.tvalue WHERE tmap.tkey < 100;

FROM (
  FROM srcpart src
  SELECT TRANSFORM(src.ds, src.key, src.value)
         USING 'cat' AS (ds, tkey, tvalue) 
  WHERE src.ds = '2008-04-08' 
  CLUSTER BY tkey 
) tmap
SELECT tmap.tkey, tmap.tvalue WHERE tmap.tkey < 100;

--multi-insert case, extract has two filter children.
CREATE TABLE MULTI1(id int, name string);
CREATE TABLE MULTI2(id int, name string);

EXPLAIN EXTENDED
FROM (
  FROM srcpart src
  SELECT TRANSFORM(src.ds, src.key, src.value)
         USING 'cat' AS (ds, tkey, tvalue)
  WHERE src.ds = '2008-04-08' 
  CLUSTER BY tkey 
) tmap
INSERT OVERWRITE TABLE multi1 SELECT tmap.tkey, tmap.tvalue WHERE tmap.tkey < 100
INSERT OVERWRITE TABLE multi2 SELECT tmap.tkey, tmap.tvalue WHERE tmap.tkey > 100;

SELECT * FROM multi1;
SELECT * FROM multi2;

DROP TABLE multi1;
DROP TABLE multi2;
