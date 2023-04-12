INSERT INTO type_of_responsibility(group_id, name)
VALUES ((SELECT id FROM responsibility_group WHERE responsibility_group.name = 'Административная'),'bebra')