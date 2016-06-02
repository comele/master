import angular from 'angular';
import adminTpl from './views/admin.tpl';
import adminSideBarTpl from './views/adminSideBar.tpl';

let adminModule = angular.module('admin', [
	adminTpl.name,
	adminSideBarTpl.name
]);

adminModule.config(($stateProvider) => {

	$stateProvider
	.state('comele.admin', {
		url: '/admin',
		views:{
			'content':{
				templateUrl: adminTpl.name,
				controller: 'adminController',
				controllerAs: 'adminCtrl'
			},
			'sideBar':{
				templateUrl: adminSideBarTpl.name,
				controller: 'adminController',
				controllerAs: 'adminCtrl'
			}
		}
	});
});

export default adminModule;

