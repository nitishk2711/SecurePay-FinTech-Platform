# Distributed Tracing

Request Flow

API Gateway
↓
Auth Service
↓
Order Service
↓
Payment Service
↓
Fraud Service
↓
Kafka
↓
Notification Service

Example

Auth Service : 10 ms

Order Service : 20 ms

Payment Service : 150 ms

Fraud Service : FAILED

Tracing quickly identifies where requests fail.
