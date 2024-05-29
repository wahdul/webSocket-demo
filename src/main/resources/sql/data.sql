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