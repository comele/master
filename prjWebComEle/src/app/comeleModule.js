import angular from 'angular';
import appModule from 'app/app';
import layoutTpl from './common/views/layout.tpl';
import 'angular-ui-bootstrap';

let comeleModule = angular.module('comele', [
	'ui.bootstrap',
	appModule.name,
	layoutTpl.name,
]);

comeleModule.config(($stateProvider) => {

	$stateProvider
	.state('comele', {
		templateUrl: layoutTpl.name,
		controller: 'layoutController',
		controllerAs: 'layoutCtrl',
		abstract: true
	})
	;
});

export default comeleModule;
