import adminModule from './adminModule';

import 'assets/css/appStyle.css!';

//Controllers
import AdminController from './controllers/AdminController';
adminModule.controller('adminController', AdminController);

export default adminModule;
