# --- Stage 1: Build Stage ---
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Copy the entire source directory into the container
COPY src ./src

# Create a directory for the compiled class files
RUN mkdir bin

# Compile all Java files inside the src directory into the bin directory
RUN javac -d bin src/*.java

# --- Stage 2: Runner Stage ---
FROM eclipse-temurin:17-jre-alpine AS runner

WORKDIR /app

# Copy the compiled class files from the builder stage
COPY --from=builder /app/bin ./bin

# Render automatically assigns a PORT environment variable
EXPOSE 10000

# Run your Main class from the bin folder
# Note: If your main method is in a different file, change "Main" to that filename
CMD ["java", "-cp", "bin", "Main"]