# -*- encoding: utf-8 -*-
# stub: teuton 2.1.9 ruby lib

Gem::Specification.new do |s|
  s.name = "teuton".freeze
  s.version = "2.1.9"

  s.required_rubygems_version = Gem::Requirement.new(">= 0".freeze) if s.respond_to? :required_rubygems_version=
  s.require_paths = ["lib".freeze]
  s.authors = ["David Vargas Ruiz".freeze]
  s.date = "2020-04-10"
  s.description = "  Intrastructure test, useful for:\n  (1) Sysadmin teachers to evaluate students remote machines.\n  (2) Sysadmin apprentices to evaluate their learning process as a game.\n  (3) Professional sysadmin to monitor remote machines.\n\n  Allow us:\n  (a) Write test units for real or virtual machines using simple DSL.\n  (b) Check compliance with requirements on remote machines.\n".freeze
  s.email = "teuton.software@protonmail.com".freeze
  s.executables = ["teuton".freeze, "check_teuton".freeze]
  s.extra_rdoc_files = ["README.md".freeze, "LICENSE".freeze, "docs/Challenge-Server-Project.md".freeze, "docs/changelog/contributions.md".freeze, "docs/changelog/servidor-de-retos.md".freeze, "docs/changelog/v2.0.md".freeze, "docs/changelog/v2.1.md".freeze, "docs/changelog/v2.2.md".freeze, "docs/commands/create_skeleton.md".freeze, "docs/commands/help.md".freeze, "docs/commands/README.md".freeze, "docs/commands/revise_test.md".freeze, "docs/commands/run_test_unit.md".freeze, "docs/commands/show_version.md".freeze, "docs/developers/01-telnet.md".freeze, "docs/developers/02-ssh.md".freeze, "docs/developers/03-encoding.md".freeze, "docs/developers/comparative.md".freeze, "docs/dsl/definition/expect.md".freeze, "docs/dsl/definition/goto.md".freeze, "docs/dsl/definition/group.md".freeze, "docs/dsl/definition/result.md".freeze, "docs/dsl/definition/run.md".freeze, "docs/dsl/definition/target.md".freeze, "docs/dsl/execution/export.md".freeze, "docs/dsl/execution/play.md".freeze, "docs/dsl/execution/send.md".freeze, "docs/dsl/execution/show.md".freeze, "docs/dsl/README.md".freeze, "docs/dsl/setting/get.md".freeze, "docs/dsl/setting/set.md".freeze, "docs/dsl/_Sidebar.md".freeze, "docs/install/manual.md".freeze, "docs/install/modes_of_use.md".freeze, "docs/install/README.md".freeze, "docs/install/scripts.md".freeze, "docs/install/tested_os.md".freeze, "docs/install/vagrant.md".freeze, "docs/learn/example-01-target.md".freeze, "docs/learn/example-02-configfile.md".freeze, "docs/learn/example-03-remote-hosts.md".freeze, "docs/learn/example-04-use.md".freeze, "docs/learn/example-05-debug.md".freeze, "docs/learn/example-11-first-test.md".freeze, "docs/learn/quick-demo.md".freeze, "docs/learn/README.md".freeze, "docs/learn/videos.md".freeze]
  s.files = ["LICENSE".freeze, "README.md".freeze, "bin/check_teuton".freeze, "bin/teuton".freeze, "docs/Challenge-Server-Project.md".freeze, "docs/changelog/contributions.md".freeze, "docs/changelog/servidor-de-retos.md".freeze, "docs/changelog/v2.0.md".freeze, "docs/changelog/v2.1.md".freeze, "docs/changelog/v2.2.md".freeze, "docs/commands/README.md".freeze, "docs/commands/create_skeleton.md".freeze, "docs/commands/help.md".freeze, "docs/commands/revise_test.md".freeze, "docs/commands/run_test_unit.md".freeze, "docs/commands/show_version.md".freeze, "docs/developers/01-telnet.md".freeze, "docs/developers/02-ssh.md".freeze, "docs/developers/03-encoding.md".freeze, "docs/developers/comparative.md".freeze, "docs/dsl/README.md".freeze, "docs/dsl/_Sidebar.md".freeze, "docs/dsl/definition/expect.md".freeze, "docs/dsl/definition/goto.md".freeze, "docs/dsl/definition/group.md".freeze, "docs/dsl/definition/result.md".freeze, "docs/dsl/definition/run.md".freeze, "docs/dsl/definition/target.md".freeze, "docs/dsl/execution/export.md".freeze, "docs/dsl/execution/play.md".freeze, "docs/dsl/execution/send.md".freeze, "docs/dsl/execution/show.md".freeze, "docs/dsl/setting/get.md".freeze, "docs/dsl/setting/set.md".freeze, "docs/install/README.md".freeze, "docs/install/manual.md".freeze, "docs/install/modes_of_use.md".freeze, "docs/install/scripts.md".freeze, "docs/install/tested_os.md".freeze, "docs/install/vagrant.md".freeze, "docs/learn/README.md".freeze, "docs/learn/example-01-target.md".freeze, "docs/learn/example-02-configfile.md".freeze, "docs/learn/example-03-remote-hosts.md".freeze, "docs/learn/example-04-use.md".freeze, "docs/learn/example-05-debug.md".freeze, "docs/learn/example-11-first-test.md".freeze, "docs/learn/quick-demo.md".freeze, "docs/learn/videos.md".freeze]
  s.homepage = "https://github.com/teuton-software/teuton/tree/devel".freeze
  s.licenses = ["GPL-3.0".freeze]
  s.post_install_message = "Thanks for installing!".freeze
  s.required_ruby_version = Gem::Requirement.new(">= 2.5.0".freeze)
  s.rubygems_version = "3.1.2".freeze
  s.summary = "Teuton (Teuton Software)".freeze

  s.installed_by_version = "3.1.2" if s.respond_to? :installed_by_version

  if s.respond_to? :specification_version then
    s.specification_version = 4
  end

  if s.respond_to? :add_runtime_dependency then
    s.add_runtime_dependency(%q<json_pure>.freeze, ["~> 2.2"])
    s.add_runtime_dependency(%q<net-sftp>.freeze, ["~> 2.1"])
    s.add_runtime_dependency(%q<net-ssh>.freeze, ["~> 5.2"])
    s.add_runtime_dependency(%q<net-telnet>.freeze, ["~> 0.1"])
    s.add_runtime_dependency(%q<rainbow>.freeze, ["~> 3.0"])
    s.add_runtime_dependency(%q<thor>.freeze, ["~> 0.20"])
    s.add_runtime_dependency(%q<terminal-table>.freeze, ["~> 1.8"])
    s.add_development_dependency(%q<minitest>.freeze, ["~> 5.11"])
    s.add_development_dependency(%q<rubocop>.freeze, ["~> 0.74"])
  else
    s.add_dependency(%q<json_pure>.freeze, ["~> 2.2"])
    s.add_dependency(%q<net-sftp>.freeze, ["~> 2.1"])
    s.add_dependency(%q<net-ssh>.freeze, ["~> 5.2"])
    s.add_dependency(%q<net-telnet>.freeze, ["~> 0.1"])
    s.add_dependency(%q<rainbow>.freeze, ["~> 3.0"])
    s.add_dependency(%q<thor>.freeze, ["~> 0.20"])
    s.add_dependency(%q<terminal-table>.freeze, ["~> 1.8"])
    s.add_dependency(%q<minitest>.freeze, ["~> 5.11"])
    s.add_dependency(%q<rubocop>.freeze, ["~> 0.74"])
  end
end
