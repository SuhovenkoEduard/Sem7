CREATE TABLE IF NOT EXISTS Hotels (
    id      INTEGER         PRIMARY KEY AUTOINCREMENT,
    name    VARCHAR (1, 50),
    score   INTEGER,
    address VARCHAR (1, 50) 
);

CREATE TABLE IF NOT EXISTS Customers (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name VARCHAR,
    last_name  VARCHAR,
    city       VARCHAR,
    country    VARCHAR,
    email      VARCHAR,
    phone      VARCHAR
);

CREATE TABLE IF NOT EXISTS Rooms (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    hotel_id    INTEGER REFERENCES Hotels (id) ON UPDATE CASCADE,
    type        VARCHAR,
    price       DECIMAL,
    max_persons INTEGER,
    looked      VARCHAR
);


CREATE TABLE IF NOT EXISTS Booking (
    id                INTEGER PRIMARY KEY AUTOINCREMENT,
    hotel_id          INTEGER REFERENCES Hotels (id) ON UPDATE CASCADE,
    customer_id       INTEGER REFERENCES Customers (id) ON UPDATE CASCADE,
    room_id           INTEGER REFERENCES Rooms (id) ON UPDATE CASCADE,
    register_date     DATE,
    accommodation_end DATE
);

