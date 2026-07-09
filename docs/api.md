# Payment APIs

## Create Order

POST /api/orders

Request

{
  "amount":1000,
  "currency":"USD"
}

Response

{
  "orderId":"ORD1001",
  "status":"CREATED"
}

---

## Create Payment

POST /api/payments

Request

{
  "orderId":"ORD1001",
  "paymentMethod":"CARD"
}

Response

{
  "paymentId":"PAY2001",
  "status":"CAPTURED"
}
