import angular from 'angular';
import comeleModule from 'app/comele';
import loginTpl from './views/login.tpl';
import mainMenuTpl from './views/mainMenu.tpl';

let loginModule = angular.module('login', [
	comeleModule.name,
	loginTpl.name,
	mainMenuTpl.name
]);

loginModule.config(($stateProvider) => {

	$stateProvider
		.state('comele.login', {
			url: '/comele',
			views:{
				'content':{
					templateUrl: loginTpl.name,
					controller: 'loginController',
					controllerAs: 'loginCtrl'
				}
			}
		})
		.state('comele.mainMenu', {
			url: '/mainMenu',
			// params: {
			// 	user: undefined,
			// 	pass: undefined,
			// 	loged: false
			// },
			views:{
				'content':{
					templateUrl: mainMenuTpl.name,
					controller: 'mainMenuController',
					controllerAs: 'mainMenuCtrl'
				}
			}
		})
		;
});

export default loginModule;
