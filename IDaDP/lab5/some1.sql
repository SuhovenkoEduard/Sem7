========== START #1 ============

select *
from rooms
where max_persons = 2 and price < 20
order by price desc

========== END #1 ============

========== START #2 ============

select *
from booking
where accommodation_end <= (select date('now')) and accommodation_end >= (SELECT date('now', '-14 days'))

========== END #2 ============

========== START #3 ============

SELECT sum((julianday(accommodation_end) - julianday(register_date))) * price as Доход_В_Сутки_За_Месяц
FROM booking inner join rooms on booking.room_id = rooms.id
where booking.register_date <= (SELECT date('now','start of month','+1 month','-1 day'))
group by booking.id

========== END #3 ============

========== START #4 ============

select *
from rooms
where looked = 'Свободен' and hotel_id = 3

========== END #4 ============

========== START #5 ============

select sum(price) as Общие_Потери_За_Сутки
from rooms
where looked = 'Свободен'

========== END #5 ============

========== START #6 ============

select hotel_id, count(id) as Количество
from rooms
where looked = 'Свободен'
group by hotel_id
order by Количество desc
limit 1

========== END #6 ============

========== START #7 ============

CREATE TABLE IF NOT EXISTS Archive (
    id                INTEGER PRIMARY KEY AUTOINCREMENT,
    hotel_id          INTEGER REFERENCES Hotels (id) ON UPDATE CASCADE,
    customer_id       INTEGER REFERENCES Customers (id) ON UPDATE CASCADE,
    room_id           INTEGER REFERENCES Rooms (id) ON UPDATE CASCADE,
    register_date     DATE,
    accommodation_end DATE
);

insert into Archive
select *
from booking
where booking.register_date < (select date('2020-01-01'));


delete from Booking
where booking.register_date < (select date('2020-01-01'))

========== END #7 ============


================================================================================
create temp table combined_temp as
select distinct rooms.id, rooms.hotel_id, rooms.type, rooms.price, rooms.max_persons
from rooms left join booking on rooms.id = booking.room_id
where room_id is NULL;
================================================================================





