INSERT INTO type_of_responsibility(group_id, name)
VALUES ((SELECT id FROM responsibility_group WHERE responsibility_group.name = 'Административная'),'bebra')
SELECT resolution_number, dln_id, v.name, v2.state_number, r.name, s.name, tor.name, rg.name FROM decree
                                                                                                      Inner Join violation v on v.id = decree.violation_id
                                                                                                      Inner Join vehicle v2 on v2.id = decree.vehicle_id
                                                                                                      INNER JOIN region r on r.id = decree.region_id
                                                                                                      INNER JOIN status s on s.id = decree.status_id
                                                                                                      Inner join type_of_responsibility tor on decree.responsibility_id = tor.id
                                                                                                      Inner Join responsibility_group rg on tor.group_id = rg.id