CREATE TRIGGER ins_act_id_user AFTER INSERT ON users
FOR EACH ROW
  INSERT INTO act_id_user(ID_,EMAIL_) VALUES (NEW.username,NEW.email);

CREATE TRIGGER del_act_id_user BEFORE DELETE ON users
FOR EACH ROW
  DELETE FROM act_id_user WHERE ID_ = OLD.username;

CREATE TRIGGER ins_act_id_group AFTER INSERT ON groups
FOR EACH ROW
  INSERT INTO act_id_group(ID_,TYPE_) VALUES (NEW.id,'group');

CREATE TRIGGER del_act_id_group BEFORE DELETE ON groups
FOR EACH ROW
  DELETE FROM act_id_group WHERE ID_ = OLD.id;

CREATE TRIGGER ins_act_id_membership AFTER INSERT ON group_members
FOR EACH ROW
  INSERT INTO act_id_membership(USER_ID_,GROUP_ID_) VALUES (NEW.username,NEW.group_id);

CREATE TRIGGER del_act_id_membership BEFORE DELETE ON group_members
FOR EACH ROW
  DELETE FROM act_id_membership WHERE USER_ID_ = OLD.username AND GROUP_ID_ = OLD.group_id;


-- //@UNDO

DROP TRIGGER ins_act_id_user;

DROP TRIGGER del_act_id_user;

DROP TRIGGER ins_act_id_group;

DROP TRIGGER del_act_id_group;

DROP TRIGGER ins_act_id_membership;

DROP TRIGGER del_act_id_membership;