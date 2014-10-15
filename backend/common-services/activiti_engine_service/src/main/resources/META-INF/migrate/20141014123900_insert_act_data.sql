
DELETE FROM act_id_membership
WHERE  Concat(user_id_, group_id_) NOT IN (SELECT Concat(username, authority)
                                           FROM   authorities);

DELETE FROM act_id_user
WHERE  id_ NOT IN (SELECT username
                   FROM   itsnow_msc.users);

DELETE FROM act_id_group
WHERE  id_ NOT IN (SELECT a.authority
                   FROM   group_authorities a
                   UNION
                   SELECT b.authority
                   FROM   authorities b);

INSERT INTO act_id_group
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
                           FROM   act_id_group);

INSERT INTO act_id_user
            (id_,
             email_)
SELECT username,
       email
FROM   itsnow_msc.users
WHERE  username NOT IN (SELECT id_
                        FROM   act_id_user);

INSERT INTO act_id_membership
            (user_id_,
             group_id_)
SELECT username,
       authority
FROM   authorities
WHERE  Concat(username, authority) NOT IN (SELECT Concat(user_id_, group_id_)
                                           FROM   act_id_membership);

-- //@UNDO