# 🚗 CI/CD Pipeline for Android App : Vehicle Data Tracking for System Analysis and Troubleshooting


## Table of Contents

- [📌 Project Overview](#-project-overview)
- [📁 Directory Structure](#-directory-structure)
- [🏗️ Project Architecture](#%EF%B8%8F-project-architecture)
- [⚙️ Technologies Used](#-llm)
- [🌐 Communication between gRPC server and gRPC client](#-data)
  - [📚 AOSP project](#-data-description)
  - [💻 Run a gRPC Server (C++) in VM Android](#-data-preparation)
  - [🖥️ Run a gRPC Client (Python) on the host machine](#-data-preparation)
- [📱 Android Application in Kotlin ](#-model)
- [🔧 GitLab CI Architecture](#-aws-setup)
- [⚡ Kubernetes Architecture & Deployment](#-aws-setup)
- [🌐 Argo CD Architecture & Deployment](#-aws-setup)
  - [🧱 Architecture Components](#-architecture-components)
  - [⚙️ SetUp Instructions](#-setup-instructions)
- [📈 Results](#-resultat)
- [🔧 Usage](#-usage)
- [🔮 Future Considerations](#-future-considerations)
- [🤝 Contributing](#-contributing)
- [👨‍💻 Project By](#project-by)

## 📌 Project Overview  

### 📚 Background:

This project, developed by Farah Elloumi and Mohamed Hedi Ben Amor as part of their professional work at Primatec Engineering, focuses on vehicle Data Tracking for system analysis and troubleshooting. Additionally, it utilizes these results to predict the anomalies in the vehicle. The solution is containerized with Docker, automated using GitLab CI and deployed using Kubernetes with Argo CD for continuous deployment.

### 🌟 The project must include:

- **✅ Real-Time Data Processing**:
  - Utilizes gRPC for real-time communication between the Android diagnostic app and the server.
  - Ensures seamless data transmission and processing for vehicle diagnostic data to detect issues and provide insights.

- **📊 Dynamic Visualization**:
  - Builds a real-time dashboard within the app to monitor vehicle health, displaying key metrics such as engine status, fuel efficiency, and error codes.
  - Provides visual representations of system performance, assisting users in decision-making for maintenance or repairs.

- **🛡️ Security and Vulnerability Scanning**: 
  - Ensure code, secrets, and container images are secure by identifying and mitigating vulnerabilities during the CI/CD pipeline.

- **✅ Automated Testing**: 
  - Automatically test all changes to ensure functionality and stability.

- **🐳 Dockerization**: 
  - Build the android application into portable Docker images.

- **☸️ Kubernetes Deployment**: 
  - Deploy services seamlessly across devolepment environments using Kubernetes clusters.

- **🔄 Integration Testing**: 
  - Validate the functionality and reliability of all services after deployment.

- **🚀 Argo CD for Continuous Delivery**:

  - Implement Argo CD to automate the deployment process, ensuring fast and reliable updates across environments.

  - Use Argo CD to manage Kubernetes resources, monitor application health, and rollback to previous versions if necessary for smooth and consistent updates.

- **🛠️ Scalable and Extensible Architecture**:
  - Employs a modular design, separating vehicle data collection, analysis, and visualization components for flexibility and scalability.
  - Allows for the integration of new diagnostic tools and data sources to support future enhancements and additional vehicle types.


## 📁 Directory Structure

```plaintext
project/
│
├── diagnostic-app/
│   ├── Source code for the Kotlin application
│   ├── .gitlab-ci.yml                              # GitLab configuration file for the CI/CD pipeline
│   ├── Dockerfile                                  # Docker configuration file for the application
│                   
├── grpc_client/
│   ├── .gitlab-ci.yml                              # GitLab configuration file for the CI/CD pipeline
│   ├── client.py                                   # Python script defining the gRPC client
│   ├── helloworld.proto                            # .proto file defining gRPC services
│      
├── grpc_server/
│   ├── .gitalb-ci.yml                              # GitLab configuration file for the CI/CD pipeline
│   ├── Android.bp                                  # .bp file for the build system
│   ├── aosp_cf.mk                                  # Makefile to add packages with the cc_binary name
│   ├── greeter_server.cc                           # Cpp script defining the gRPC server
│   ├── helloworld.proto                            # .proto file defining gRPC services
│       
└── README.md                                       # Project documentation 
└── manifest.xml                                    # XML code to link three projects within the same GitLab group
```

This guide will walk you through the process of setting up the AOSP build environment, compiling a C++ binary (`server.cpp`) to run in an Android VM (Cuttlefish), and establishing communication between a **gRPC client** (host) and a **gRPC server** (VM).

## 🖥️ **Prerequisites** 🛠️

To successfully set up and run your C++ binary with gRPC on the Android VM, you'll need the following tools and requirements:

### 🧰 **System Requirements**:
- **OS**: Ubuntu 20.04+ 
- **Disk space**: 400 GB+ free
- **RAM**: 16 GB minimum
- **Tools**:
  - **Git**
  - **Python**
  - **Repo**
  - **OpenJDK (11 or 17)**

### 📦 **Install Prerequisites**:

#### 1️⃣ **Install Git**:
```bash
sudo apt update
sudo apt install git -y
git --version
```

#### 2️⃣ **Install Python**:
```bash
sudo apt install python3 python3-pip -y
python3 --version
```

#### 3️⃣ **Install Repo**:
```bash
mkdir -p ~/.bin
curl https://storage.googleapis.com/git-repo-downloads/repo > ~/.bin/repo
chmod +x ~/.bin/repo
echo 'export PATH="$HOME/.bin:$PATH"' >> ~/.bashrc
source ~/.bashrc
repo --version
```

#### 4️⃣ **Install OpenJDK**:
```bash
sudo apt install openjdk-11-jdk -y
java -version
```

### ⚠️ **Memory Issues**:
If you're using 16GB of RAM, you'll need to add swap memory to avoid compilation issues.
```bash
sudo fallocate -l 16G /swapfile
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile
```

To make it permanent:
```bash
echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
```
Check memory usage:
```bash
free -h
```
## 💻 **Clone AOSP**:
```bash
mkdir ~/android-aosp
cd ~/android-aosp
repo init -u https://android.googlesource.com/platform/manifest -b android14-gsi
repo sync -j4
```

## 🛠️ **Configure the Build**:
```bash
source build/envsetup.sh
lunch aosp_cf_x86_64_phone-trunk_staging-userdebug  # or 'lunch 14'
echo "$TARGET_PRODUCT-$TARGET_BUILD_VARIANT"
```
### 💡 **Fix Common Build Issues**:
**Clang Library Issue**:

If you encounter the error clang.real: error while loading shared libraries: libncurses.so.5, run:
```bash
sudo apt install libncurses5
```
**For Bison Errors**:

Update it:
```bash
sudo apt install bison
bison --version
```

## 💻 **Build the Code** 🛠️
```bash
make
```

## 🚀 **gRPC Server Setup in AOSP**
### Install Dependencies 🧰

Run the following command to install the necessary dependencies:

```bash
sudo apt install clang llvm android-tools-adb android-tools-fastboot -y
```

### 📄 **Generate gRPC and C++ Files**

In the android-aosp/external/grpc-grpc/examples/protos/ directory, run the following commands to generate the necessary files:

```bash
aprotoc --grpc_out=. --plugin=protoc-gen-grpc=/home/user/android-aosp/out/host/linux-x86/bin/protoc-gen-grpc-cpp-plugin helloworld.proto
aprotoc --cpp_out=. helloworld.proto
```

### 📂 **Move Generated Files**

Move the generated files to android-aosp/external/grpc-grpc/examples/cpp/helloworld.

### ⚙️ **Create android.bp File**

Create a file named android.bp with the following content:
```bash
cc_binary {
  name: "grpc_server",
  vendor: true,
  cflags: [
    "-Wno-error",
    "-Wno-unused-parameter",
    "-Wno-implicit-fallthrough",
    "-Wno-unused-result",
    "-g",
  ],
  srcs: [
    "greeter_server.cc",
    "cmake/build/helloworld.grpc.pb.cc",
    "cmake/build/helloworld.pb.cc",
  ],
  include_dirs: [
    "external/grpc-grpc/include",
    "external/protobuf/src",
    "external/grpc-grpc/examples/cpp/helloworld/cmake/build/",
  ],
  shared_libs: [
    "libprotobuf-cpp-full",
    "libgrpc++_unsecure",
    "libz",
    "libc++",
    "libm",
    "libdl",
  ],
}
```

### 📝 **Modify AOSP Makefile**

In android-aosp/device/google/cuttlefish/vsoc_x86_64/phone/aosp_cf.mk, add:
```bash
PRODUCT_PACKAGES += \
  grpc_server
```
### ⚙️ **Build the Project**

Run the following command to build the project:
```bash
make
```

## 🌐 **Run the Android VM (Cuttlefish)**
**Start the Android VM** 🚀

Create the Android VM instance:
```bash
acloud create --local-instance 1 --local-image
```
**Access ADB Shell** 📱
```bash
adb root
adb shell
```
**Verify the Binary** ✅

Check if the binary exists:
```bash
ls /vendor/bin/grpc_server
```
**Run the gRPC Server** 🖥️

Run the server:
```bash
/vendor/bin/grpc_server
```
**Check the Server is Listening** 📡
```bash
netstat -tulnp | grep 50051
ip addr show
```
Expected output:
```bash
Server listening on 0.0.0.0:50051
```

## 🖥️ **Create and Run Python gRPC Client**
### 📦 **Install Python Dependencies**

Install the necessary Python dependencies:
```bash
pip3 install grpcio grpcio-tools
```
### 💻 **Client Code (client.py)**

Create the client.py file with the following code:
```bash
import grpc
import helloworld_pb2
import helloworld_pb2_grpc

def run():
    with grpc.insecure_channel("your_vm_ip:50051") as channel:
        stub = helloworld_pb2_grpc.GreeterStub(channel)
        response = stub.SayHello(helloworld_pb2.HelloRequest(name="Farah"))
        print("Server response:", response.message)

if __name__ == "__main__":
    run()
```
### 🛠️ **Generate Python Code from .proto File**

Run the following command to generate the Python files from the .proto file:
```bash
python -m grpc_tools.protoc -I./ --python_out=./ --grpc_python_out=./ helloworld.proto
```
### ▶️ **Run the Python Client**

Run the client with the following command:
```bash
python3 client.py
```
### 🎉 **Expected Output**
```bash
Server response: Hello Farah
```

>>>>>>> bc79cba (Add grpc-server and grpc-client)
