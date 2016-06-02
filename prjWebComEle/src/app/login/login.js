import loginModule from './loginModule';

import LoginController from './controllers/LoginController';
loginModule.controller('loginController', LoginController);

import MainMenuController from './controllers/MainMenuController';
loginModule.controller('mainMenuController', MainMenuController);


export default loginModule;
