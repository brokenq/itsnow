DELETE FROM site_depts;
DELETE FROM sites;
DELETE FROM departments WHERE parent_id IS NOT NULL;
DELETE FROM departments;
DELETE FROM work_times;
DELETE FROM dictionaries;
DELETE FROM staffs;
DELETE FROM itsnow_msc.users;
DELETE FROM itsnow_msc.accounts;