insert into worker select * from (
select 0 id, 'Emily Johnson' name union
select 1, 'Sophia Rodriguez' union
select 2, 'James Brown' union
select 3, 'Ethan Taylor' union
select 4, 'David Smith' union
select 5, 'Maya Johnson' union
select 6, 'Oliver Patel' union
select 7, 'Sophia Nguyen' union
select 8, 'Elena Marcus' union
select 9, 'Alexandria Sebastian'
) x where not exists(select * from worker w where w.name = x.name);
insert into transport_type select * from (
select 0 id, 'Pick-up cargo' label union
select 1, 'Air Lodgment' union
select 2, 'Air Pick-up and Lodge (express clients)' union
select 3, 'Perishables Lodgment' union
select 4, 'Import Terminal Pick-up' union
select 5, 'FBA Delivery' union
select 6, 'Interstate delivery' union
select 7, 'Local delivery' union
select 8, 'Other'
) x where not exists(select * from transport_type w where w.id = x.id);
insert into unit select * from (
select 0 id, 'Pieces' label union
select 1, 'Kilograms' union
select 2, 'PMC' union
select 3, 'AKE'
) x where not exists(select * from unit w where w.id = x.id);
insert into status select * from (
select 0 id, 'Job Confirmation' label union
select 1, 'On Route' union
select 2, 'Arrived' union
select 3, 'Picked-up' union
select 4, 'Delivered' union
select 5, 'Complete'
) x where not exists(select * from status w where w.id = x.id);