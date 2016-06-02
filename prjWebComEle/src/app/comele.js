import comeleModule from './comeleModule';
import 'assets/css/appStyle.css!';

//Controllers
import LayoutController from './common/controllers/LayoutController';
comeleModule.controller('layoutController', LayoutController);

//Models
import AppModel from './common/models/AppModel';
comeleModule.value('appModel', new AppModel());

//Constantes
import appConstant from './common/resource/appConstant';
comeleModule.constant('appConstant', appConstant);

export default comeleModule;
