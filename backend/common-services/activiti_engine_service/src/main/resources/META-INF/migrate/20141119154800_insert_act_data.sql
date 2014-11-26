
-- DELETE FROM ACT_ID_MEMBERSHIP
-- WHERE  Concat(user_id_, group_id_) NOT IN (SELECT Concat(username, authority)
--                                            FROM   authorities);

DELETE FROM ACT_ID_USER
WHERE  id_ NOT IN (SELECT username
                   FROM   itsnow_msc.users);

DELETE FROM ACT_ID_GROUP
WHERE  id_ NOT IN (SELECT a.authority
                   FROM   group_authorities a
                   UNION
                   SELECT b.authority
                   FROM   authorities b);

INSERT INTO ACT_ID_GROUP
            (id_,
             name_,
             type_)
SELECT c.authority,
       c.authority,
       'group'
FROM   (SELECT a.authority
        FROM   group_authorities a
        UNION
        SELECT b.authority
        FROM   authorities b) c
WHERE  c.authority NOT IN (SELECT id_
                           FROM   ACT_ID_GROUP);

INSERT INTO ACT_ID_USER
            (id_,
             email_)
SELECT username,
       email
FROM   itsnow_msc.users
WHERE  username NOT IN (SELECT id_
                        FROM   ACT_ID_USER);

INSERT INTO ACT_ID_MEMBERSHIP
            (user_id_,
             group_id_)
SELECT username,
       authority
FROM   authorities
WHERE  Concat(username, authority) NOT IN (SELECT Concat(user_id_, group_id_)
                                           FROM   ACT_ID_MEMBERSHIP);

-- //@UNDO