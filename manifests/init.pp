class nginx {

        $packages = ["epel-release", "nginx"]
        package { $packages:
                ensure => installed,
        }

        service { "nginx":
                ensure => running,
                require => Package['nginx'],
                subscribe => File["default.conf"]
        }

        file { "default.conf":
                owner => 'root',
                path => '/etc/nginx/conf.d/default.conf',
                require => Package['nginx'],
                notify => Service['nginx'],
                content => template('/etc/puppet/modules/nginx/templates/default.conf.erb')
        }
}
