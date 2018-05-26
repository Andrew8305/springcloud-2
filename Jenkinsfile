pipeline {
  agent any
  environment {
    DOCKER_IMAGE = null
  }
  stages {
    stage('Build') {
      agent {
          docker {
              image 'maven:3-alpine'
            // do some caching on maven here
              args '-v $HOME/.m2:/root/.m2'
	      reuseNode true
          }
      }
      steps {
        sh 'mvn clean install'
      }
    }
    stage('createnetwork') {
      steps {
        script {
   			try {	
				docker.script.sh "docker network create cloud"
			} catch(e) {
	    		echo "network cloud already exists"
          	}
        }
      }
    }
    stage('buildregistry') {
      steps {
        script {
          DOCKER_IMAGE = docker.build("registry:latest","./registry")
        }
      }
    }
    stage('runregistry') {
      steps {
        script {
   			try {	
				id = "registry"
				docker.script.sh "docker stop ${id} && docker rm -f ${id}"
			} catch(e) {
	    		echo "container ${id} not found"
          	}
	        DOCKER_IMAGE.run('--network cloud --network-alias registry --name registry -p 8082:8082')
        }
      }
    }
    stage('buildconfig') {
      steps {
        script {
          DOCKER_IMAGE = docker.build("config:latest","./config")
        }
      }
    }
    stage('runconfig') {
      steps {
        script {
   			try {	
				id = "config"
				docker.script.sh "docker stop ${id} && docker rm -f ${id}"
			} catch(e) {
	    		echo "container ${id} not found"
          	}
	        DOCKER_IMAGE.run('--network cloud --network-alias config --name config -p 8888:8888')
        }
      }
    }
    stage('buildcommentstore') {
      steps {
        script {
          DOCKER_IMAGE = docker.build("commentstore:latest","./comment-store")
        }
      }
    }
    stage('runcommentstore') {
      steps {
        script {
   			try {	
				id = "commentstore"
				docker.script.sh "docker stop ${id} && docker rm -f ${id}"
			} catch(e) {
	    		echo "container ${id} not found"
          	}
	        DOCKER_IMAGE.run('--network cloud --network-alias commentstore --name commentstore -p 8080:8080')
        }
      }
    }
    stage('buildconsumer') {
      steps {
        script {
          DOCKER_IMAGE = docker.build("consumer:latest","./consumer")
        }
      }
    }
    stage('runconsumer') {
      steps {
        script {
   			try {	
				id = "consumer"
				docker.script.sh "docker stop ${id} && docker rm -f ${id}"
			} catch(e) {
	    		echo "container ${id} not found"
          	}
	        DOCKER_IMAGE.run('--network cloud --network-alias consumer --name comsumer -p 8081:8081')
        }
      }
    }
    stage('buildzipkin') {
      steps {
        script {
          DOCKER_IMAGE = docker.build("zipkin:latest","./consumer")
        }
      }
    }
    stage('runzipkin') {
      steps {
        script {
   			try {	
				id = "zipkin"
				docker.script.sh "docker stop ${id} && docker rm -f ${id}"
			} catch(e) {
	    		echo "container ${id} not found"
          	}
	        DOCKER_IMAGE.run('--network cloud --network-alias zipkin --name zipkin -p 9411:9411')
        }
      }
    }
  }
}
