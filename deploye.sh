
#!/bin/bash
set -Eeuo pipefail

COMPOSE_FILE="compose.yaml"

echo "=========================================="
echo "   SecurePay FinTech Platform Deployment"
echo "=========================================="

# 1. Stop and remove existing Compose containers
echo "[1/4] Stopping existing containers..."
docker compose -f "$COMPOSE_FILE" down

docker compose -f "$COMPOSE_FILE" down --remove-orphans

# 3. Start all services in detached mode
echo "[3/4] Starting all services..."
docker compose -f "$COMPOSE_FILE" up -d

# 4. Display service status
echo "[4/4] Checking container status..."
docker compose -f "$COMPOSE_FILE" ps

echo "=========================================="
echo " Deployment completed successfully!"
echo "=========================================="
