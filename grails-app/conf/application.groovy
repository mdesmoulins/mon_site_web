

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'recettes.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'recettes.UserRole'
grails.plugin.springsecurity.authority.className = 'recettes.Role'
grails.plugin.springsecurity.password.algorithm = 'SHA-256'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
		[pattern: '/',               access: ['permitAll']],
		[pattern: '/login/**',               access: ['permitAll']],
	[pattern: '/mesRecettes',    access: ['permitAll']],
	[pattern: '/mesRecettes/index/**',    access: ['permitAll']],
	[pattern: '/mesRecettes/show/**', access: ['permitAll']],
		[pattern: '/mesRecettes/update/**', access: ['ROLE_CHEF']],
		[pattern: '/mesRecettes/create/**', access: ['ROLE_CHEF']],
		[pattern: '/mesRecettes/edit/**', access: ['ROLE_CHEF']],
		[pattern: '/ustensiles/**', access: ['ROLE_CHEF']],
		[pattern: '/mesRecettes/deleteSelected/**', access: ['ROLE_CHEF']],
		[pattern: '/mesRecettes/search/**',          access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

