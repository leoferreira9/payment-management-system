create table payment (
        id BIGINT NOT NULL AUTO_INCREMENT,
        value DECIMAL(9,2) NOT NULL,
        payment_deadline DATETIME(6) NOT NULL,
        description VARCHAR(100),
        payment_type ENUM ('BOLETO','CREDIT_CARD','DEBIT_CARD','PIX') NOT NULL,
        status ENUM ('CANCELLED','PAID','PENDING') NOT NULL,
        PRIMARY KEY (id)
);

create table payment_record (
        id BIGINT NOT NULL AUTO_INCREMENT,
        value DECIMAL(9,2) NOT NULL,
        event_date DATETIME(6) NOT NULL,
        payment_deadline_snapshot DATETIME(6) NOT NULL,
        payment_id BIGINT NOT NULL,
        description VARCHAR(100),
        payment_type ENUM ('BOLETO','CREDIT_CARD','DEBIT_CARD','PIX') NOT NULL,
        status ENUM ('CANCELLED','PAID','PENDING','REFUNDED') NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT fk_payment_record_payment
            FOREIGN KEY (payment_id)
            REFERENCES payment(id)
);