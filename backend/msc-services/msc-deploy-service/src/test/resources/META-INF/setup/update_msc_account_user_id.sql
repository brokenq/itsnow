SET @MSC_ID = (SELECT id from accounts where sn = 'msc');
SET @ADMIN_ID = (SELECT id FROM users where username = 'admin');
UPDATE accounts SET user_id = @ADMIN_ID WHERE id = @MSC_ID;
