-- // insert_ci_relation_types
-- Migration SQL that makes the change goes here.

INSERT ci_relation_types(id, name, reverse_name, source_filter, dest_filter, css, description) VALUES
  ( 1, 'depends_on',          'used_by',              NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Runnable depends on container'),
  ( 2, 'uses',                'used_by',              NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Something uses another'),
  ( 3, 'uses',                'owned_by',             NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Something uses another'),
  ( 4, 'sends_data_to',       'receives_data_from',   NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Someone sends data to another'),
  ( 5, 'runs',                'runs_on',              NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Some system runs on another'),
  ( 6, 'connected_to',        'connected_to',         NULL,       NULL,         '{array: false, line: solid, weight: 10}', 'Some system connected to another'),
  ( 7, 'subscribes_to',       'subscribed_by',        NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Some one subscribes to another'),
  ( 8, 'impacts',             'impacted_by',          NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Some system impacts another'),
  ( 9, 'submits',             'submitted_by',         NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Some one submits something'),
  (10, 'supports',            'supported_by',         NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Some group supports some system'),
  (11, 'consists_of',         'are_parts_of',         NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Something consists of another'),
  (12, 'author_of',           'is_written_by',        'People',   'Document',   '{array: true,  line: solid, weight: 10}', 'People is author of documents'),
  (13, 'is_edited_by',        'editor_of',            'Document', 'Technician', '{array: true,  line: solid, weight: 10}', 'Document is edited by technician'),
  (14, 'hosted_on',           'hosts',                'Application', 'Host',    '{array: true,  line: solid, weight: 10}', 'Business service is hosted on server'),
  (15, 'enables',             'is_enabled_by',        NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'IT service enables by business service'),
  (16, 'includes',            'member_of',            NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Network includes switches'),
  (17, 'contains',            'in_rack',              NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Rack contains server'),
  (18, 'contains',            'member_of',            NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Something contains another'),
  (19, 'located_in',          'houses',               NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Rack located in data center'),
  (20, 'exchanges_data_with', 'exchanges_data_with',  NULL,       NULL,         '{array: false, line: solid, weight: 10}', 'Some system exchange data with another'),
  (21, 'backed_up_by',        'backed_up_by',         NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Some system backed up by another'),
  (22, 'managed_by',          'manages',              NULL,       'People',     '{array: true,  line: solid, weight: 10}', 'Some system is managed by someone'),
  (23, 'virtualizes',         'virtualized_by',       NULL,       NULL,         '{array: true,  line: solid, weight: 10}', 'Something virtualizes another');

-- //@UNDO
-- SQL to undo the change goes here.

TRUNCATE TABLE ci_relation_types;
