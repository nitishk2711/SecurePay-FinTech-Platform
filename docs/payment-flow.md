# Payment Flow

## Customer Authentication

John logs into TechStore.

Email:
john@gmail.com

Password:
********

Auth Service validates credentials and generates a JWT token.

↓

## Order Creation

John clicks Buy iPhone.

Order Service creates:

Order ID : ORD1001

Status : CREATED

↓

## Payment Processing

Payment Service creates:

Payment ID : PAY2001

Card details are validated.

If successful:

Status : CAPTURED

↓

## Account Update

John's balance changes:

Before : $5000

After : $4000
