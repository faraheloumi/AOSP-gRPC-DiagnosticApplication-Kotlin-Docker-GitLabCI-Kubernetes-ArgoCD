import grpc
import helloworld_pb2
import helloworld_pb2_grpc

def run():

    with grpc.insecure_channel("192.168.97.2:50051") as channel:

        stub = helloworld_pb2_grpc.GreeterStub(channel)

        # Envoi d'une requête avec le nom "Farah"
        response = stub.SayHello(helloworld_pb2.HelloRequest(name="Farah"))
        print("Réponse du serveur:", response.message)

if __name__ == '__main__':
    run()
    
