# Use a Debian-based OpenJDK 17 image (not Alpine)
FROM openjdk:17-jdk-slim

# Set environment variables for Android SDK
ENV ANDROID_SDK_ROOT=/opt/android-sdk
ENV PATH=$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools:$PATH

# Install dependencies
RUN apt-get update && apt-get install -y \
    unzip \
    wget \
    git \
    curl \
    && rm -rf /var/lib/apt/lists/

# Download and install Android SDK command-line tools
RUN mkdir -p $ANDROID_SDK_ROOT/cmdline-tools && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip -O android-sdk.zip && \
    unzip android-sdk.zip -d $ANDROID_SDK_ROOT/cmdline-tools && \
    mv $ANDROID_SDK_ROOT/cmdline-tools/cmdline-tools $ANDROID_SDK_ROOT/cmdline-tools/latest && \
    rm android-sdk.zip

# Accept Android SDK licenses
RUN yes | sdkmanager --licenses

# Install necessary Android SDK components
RUN sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

# Set working directorydocker images

WORKDIR /app

# Copy the project files into the container
COPY . .

# Make Gradle wrapper executable
RUN chmod +x ./gradlew

# Build the Kotlin-based Android app
RUN ./gradlew assembleDebug

# Set default command (optional)
CMD tail -f /dev/null

