-- // insert_ci_relation_types
-- Migration SQL that makes the change goes here.

INSERT ci_relation_types(name, reverse_name, source_filter, dest_filter, css, description) VALUES
  ('depends_on',          'used_by',              NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Runnable depends on container'),
  ('uses',                'used_by',              NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Something uses another'),
  ('uses',                'owned_by',             NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Something uses another'),
  ('sends_data_to',       'receives_data_from',   NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Someone sends data to another'),
  ('runs',                'runs_on',              NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Some system runs on another'),
  ('connected_to',        'connected_to',         NULL,       NULL,         '{array: false, line: solid, weight: 10}', 'Some system connected to another'),
  ('subscribes_to',       'subscribed_by',        NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Some one subscribes to another'),
  ('impacts',             'impacted_by',          NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Some system impacts another'),
  ('submits',             'submitted_by',         NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Some one submits something'),
  ('supports',            'supported_by',         NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Some group supports some system'),
  ('consists_of',         'are_parts_of',         NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Something consists of another'),
  ('author_of',           'is_written_by',        'People',   'Document',   '{array: true,  line: solid, weight: 10}', 'People is author of documents'),
  ('is_edited_by',        'editor_of',            'Document', 'Technician', '{array: true,  line: solid, weight: 10}', 'Document is edited by technician'),
  ('hosted_on',           'hosts',                'Application', 'Host',    '{array: true,  line: solid, weight: 10}', 'Business service is hosted on server'),
  ('enables',             'is_enabled_by',        NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'IT service enables by business service'),
  ('includes',            'member_of',            NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Network includes switches'),
  ('contains',            'in_rack',              NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Rack contains server'),
  ('contains',            'member_of',            NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Something contains another'),
  ('located_in',          'houses',               NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Rack located in data center'),
  ('exchanges_data_with', 'exchanges_data_with',  NULL,       NULL,         '{array: false, line: solid, weight: 10}', 'Some system exchange data with another'),
  ('backed_up_by',        'backed_up_by',         NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Some system backed up by another'),
  ('managed_by',          'manages',              NULL,       'People',     '{array: true,  line: solid, weight: 10}', 'Some system is managed by someone'),
  ('virtualizes',         'virtualized_by',       NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Something virtualizes another');

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE ci_relation_types;
