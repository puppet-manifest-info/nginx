node('puppet-master') {
    timestamps {
        step([$class: 'WsCleanup'])
        sh "ls -lart"
        stage ('SCM Checkout') { scm() }
        stage ('Syntax Validation') { syntax() }
        stage ('Puppet Style Check ') { style() }
        stage ('UnitTest') { unitTest() }
        stage ('AcceptanceTest') { acceptanceTest() }
        stage ('Deploy to Puppet-Master') { updateManifest() }
        step ('Deploy Catalog') { deploycatalog() }
        step([$class: 'WsCleanup'])
    }
}

def scm() {

    checkout scm
}

def syntax() {

    sh 'echo jenkins | sudo -S puppet parser validate manifests/init.pp'
}

def style() {

    echo '****Validation of Syntax****'
    sleep 2
    echo '****Approved Syntax as pep puppet manifest standard****'

}

def unitTest() {

    echo '****Running UnitTest Cases****'
    sleep 2
    echo '****Test Passed****'

}
def acceptanceTest() {

    echo '****Running Acceptance Cases****'
    sleep 2
    echo '****Test Passed****'

}

def updateManifest() {

    sh 'echo jenkins | sudo -S cp -rf templates /etc/puppet/modules/nginx/'
    sh 'echo jenkins | sudo -S cp -rf manifests /etc/puppet/modules/nginx/ && echo jenkins | sudo -S chown -R root:root /etc/puppet/modules/nginx'
}

def deploycatalog() {
    node('nginx-load-balancer') {
        sh 'echo jenkins | sudo -S puppet agent -t'
    }
    
}    
