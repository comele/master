import 'jquery';
import angular from 'angular';
import 'twbs/bootstrap/css/bootstrap.css!';
import 'twbs/bootstrap/css/bootstrap-theme.css!';

import router from 'oclazyload-systemjs-router';
import futureRoutes from 'app/routes.json!';

let appModule = angular.module('app', []);

appModule.config(router(appModule, futureRoutes));

appModule.config(function($locationProvider, $httpProvider, $urlRouterProvider) {
	// $locationProvider.html5Mode({
	// 	enabled: true,
	// 	requireBase: false
	// });
	$httpProvider.useApplyAsync(true);
	return $urlRouterProvider.otherwise('/comele');
});

angular.element(document).ready(function() {
	angular.bootstrap(document.body, ['app'], {
		strictDi: false
	});
});

export default appModule;
