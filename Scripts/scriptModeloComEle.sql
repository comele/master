/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     11/05/2016 8:22:58                           */
/*==============================================================*/
--Solo ejecutar si no se creo la base de datos
CREATE DATABASE bddcomele
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Spanish_Spain.1252'
       LC_CTYPE = 'Spanish_Spain.1252'
       CONNECTION LIMIT = -1;

--para ejecutar se debe seleccionar la base de datos a usar
/*==============================================================*/
/* Table: TAUTCOMPROBANTE                                       */
/*==============================================================*/
create table TAUTCOMPROBANTE (
   IDAUTCOMP            DECIMAL(9)           not null,
   CLAVEDEACCESO        DECIMAL(49,0)        null,
   NUMAUTORIZACION      DECIMAL(37,0)        null,
   ESTADO               VARCHAR(4)           null,
   IDCOMPROBANTE        DECIMAL(9,0)         null,
   IDCLAVESCONTINGENCIA DECIMAL(9,0)         null,
   IDFIRMAELECTRONICA   DECIMAL(9,0)         null,
   IDERRORSRI           DECIMAL(9,0)         null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TAUTCOMPROBANTE primary key (IDAUTCOMP)
);

/*==============================================================*/
/* Table: TCATALOGOARTICULOS                                    */
/*==============================================================*/
create table TCATALOGOARTICULOS (
   IDARTICULO           DECIMAL(9,0)         not null,
   CODIGOBARRAS         DECIMAL(64,0)        null,
   DESCRIPCIONART       VARCHAR(128)         null,
   PRECIOUNISINIVA      DECIMAL(10,4)        null,
   APLICAIVA            VARCHAR(2)           null,
   PORIVAAPLICAR        VARCHAR(2)           null,
   TIPODEDUCIBLE        VARCHAR(64)          null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHARESGISTRO       DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TCATALOGOARTICULOS primary key (IDARTICULO)
);

/*==============================================================*/
/* Table: TCLAVESCONTINGENCIA                                   */
/*==============================================================*/
create table TCLAVESCONTINGENCIA (
   IDCLAVESCONTINGENCIA DECIMAL(9,0)         not null,
   CLAVECONTINGENCIA    DECIMAL(49,0)        null,
   ESTADO               VARCHAR(4)           null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TCLAVESCONTINGENCIA primary key (IDCLAVESCONTINGENCIA)
);

/*==============================================================*/
/* Table: TCLIENTE                                              */
/*==============================================================*/
create table TCLIENTE (
   IDCLIENTE            DECIMAL(9,0)         not null,
   NUMDOCUMENTO         DECIMAL(13,0)        null,
   PRIMERNOMBRE         VARCHAR(64)          null,
   SEGUNDONOMBRE        VARCHAR(64)          null,
   PRIMERAPELLIDO       VARCHAR(64)          null,
   SEGUNDOAPELLIDO      VARCHAR(64)          null,
   TELEFONO             DECIMAL(10,0)        null,
   DIRECCION            VARCHAR(128)         null,
   EMAIL                VARCHAR(64)          null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   IDTIPODOCUMENTO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TCLIENTE primary key (IDCLIENTE)
);

/*==============================================================*/
/* Table: TCOMPROBANTE                                          */
/*==============================================================*/
create table TCOMPROBANTE (
   IDCOMPROBANTE        DECIMAL(9,0)         not null,
   IDTIPOCOMPROBANTE    DECIMAL(9,0)         null,
   AMBIENTE             INT4                 null,
   TIPOEMISION          INT4                 null,
   TOTALBRUTO           DECIMAL(10,2)        null,
   DESCUENTOS           DECIMAL(10,2)        null,
   TOTALNETO            DECIMAL(10,2)        null,
   BASESINIMP           DECIMAL(10,2)        null,
   BASECONIMP           DECIMAL(10,2)        null,
   IVA                  INT4                 null,
   PORIVAAPLICADO       INT4                 null,
   TOTALFACTURA         DECIMAL(10,2)        null,
   NUMCOMPROBANTE       INT4                 null,
   IDCLIENTE            DECIMAL(9,0)         null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TCOMPROBANTE primary key (IDCOMPROBANTE)
);

/*==============================================================*/
/* Table: TDEDUCIBLEFACTURA                                     */
/*==============================================================*/
create table TDEDUCIBLEFACTURA (
   IDDEDUCIBLEFACTURA   DECIMAL(9,0)         not null,
   IDFACTCLIENTEEXT     DECIMAL(9,0)         null,
   TOTALDEDUCIBLEFACT   DECIMAL(10,4)        null,
   IDTIPODEDUCIBLE      DECIMAL(9,0)         null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TDEDUCIBLEFACTURA primary key (IDDEDUCIBLEFACTURA)
);

/*==============================================================*/
/* Table: TDEDUCIBLES                                           */
/*==============================================================*/
create table TDEDUCIBLES (
   IDDEDUCIBLE          DECIMAL(9,0)         not null,
   IDPROVEEDOR          DECIMAL(9,0)         null,
   IDTIPODEDUCIBLE      DECIMAL(9,0)         null,
   IDCLIENTE            DECIMAL(9,0)         null,
   VALORTOTALDEDUCIBLE  DECIMAL(10,4)        null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TDEDUCIBLES primary key (IDDEDUCIBLE)
);

/*==============================================================*/
/* Table: TDETALLECOMPROBANTE                                   */
/*==============================================================*/
create table TDETALLECOMPROBANTE (
   IDDETALLE            DECIMAL(9,0)         not null,
   CODIGODEBARRAS       DECIMAL(64,0)        null,
   DESCRIPCION          VARCHAR(128)         null,
   PRECIOUNISINIMP      DECIMAL(10,4)        null,
   PRECIOUNICONIMO      DECIMAL(10,4)        null,
   IVAAPLICADO          INT4                 null,
   IDCOMPROBANTE        DECIMAL(9,0)         null,
   IDARTICULO           DECIMAL(9,0)         null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TDETALLECOMPROBANTE primary key (IDDETALLE)
);

/*==============================================================*/
/* Table: TDETALLEFACTEXT                                       */
/*==============================================================*/
create table TDETALLEFACTEXT (
   IDDETALLEFACTEXT     DECIMAL(9,0)         not null,
   CODIGODEBARRAS       DECIMAL(64,0)        null,
   DESCRIPCION          VARCHAR(128)         null,
   PRECIOUNISINIMP      DECIMAL(10,4)        null,
   PRECIOUNICONIMO      DECIMAL(10,4)        null,
   IVAAPLICADO          INT4                 null,
   IDFACTCLIENTEEXT     DECIMAL(9,0)         null,
   IDTIPODEDUCIBLE      DECIMAL(9,0)         null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TDETALLEFACTEXT primary key (IDDETALLEFACTEXT)
);

/*==============================================================*/
/* Table: TERRORESAUTORIZACIONSRI                               */
/*==============================================================*/
create table TERRORESAUTORIZACIONSRI (
   IDERRORSRI           DECIMAL(9,0)         not null,
   CODIGOERRORSRI       VARCHAR(10)          null,
   DESCRIPCIONERRORSRI  VARCHAR(200)         null,
   POSIBLESOLUCIONERRORSRI VARCHAR(400)         null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        TIMESTAMP            null,
   FECHAMODIFICACION    TIMESTAMP            null,
   constraint PK_TERRORESAUTORIZACIONSRI primary key (IDERRORSRI)
);

/*==============================================================*/
/* Table: TESTABLECIMIENTO                                      */
/*==============================================================*/
create table TESTABLECIMIENTO (
   IDESTABLECIMIENTO    DECIMAL(9,0)         not null,
   NOMBREESTABLECIMIENTO VARCHAR(128)         null,
   CODIGOESTABLECIMIENTO VARCHAR(64)          not null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TESTABLECIMIENTO primary key (IDESTABLECIMIENTO)
);

/*==============================================================*/
/* Table: TFACTURACLIENTEEXTERNO                                */
/*==============================================================*/
create table TFACTURACLIENTEEXTERNO (
   IDFACTCLIENTEEXT     DECIMAL(9,0)         not null,
   AMBIENTE             INT4                 null,
   TIPOEMISION          INT4                 null,
   TOTALBRUTO           DECIMAL(10,2)        null,
   DESCUENTOS           DECIMAL(10,2)        null,
   TOTALNETO            DECIMAL(10,2)        null,
   BASESINIMP           DECIMAL(10,2)        null,
   BASECONIMP           DECIMAL(10,2)        null,
   IVA                  INT4                 null,
   PORIVAAPLICADO       INT4                 null,
   TOTALFACTURA         DECIMAL(10,2)        null,
   NUMCOMPROBANTE       INT4                 null,
   IDPROVEEDOR          DECIMAL(9,0)         null,
   IDCLIENTE            DECIMAL(9,0)         null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TFACTURACLIENTEEXTERNO primary key (IDFACTCLIENTEEXT)
);

/*==============================================================*/
/* Table: TFIRMAELECTRONICA                                     */
/*==============================================================*/
create table TFIRMAELECTRONICA (
   IDFIRMAELECTRONICA   DECIMAL(9,0)         not null,
   ARCHIVOFIRELE        CHAR(2048)           null,
   ESTADOFIRELE         VARCHAR(4)           null,
   VIGENCIA             DATE                 null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TFIRMAELECTRONICA primary key (IDFIRMAELECTRONICA)
);

/*==============================================================*/
/* Table: TMENU                                                 */
/*==============================================================*/
create table TMENU (
   IDMENU               DECIMAL(9,0)         not null,
   IDMENUPADRE          DECIMAL(9,0)         null,
   NOMBREMENU           VARCHAR(128)         null,
   DESCRIPCIONMENU      VARCHAR(1024)        null,
   URLACCESO		VARCHAR(128)         null,
   VISIBLE		VARCHAR(2)	     null,
   ORDEN		DECIMAL(5,0)   	     null,
   MOSTRARENPANEL	VARCHAR(2)     	     null,
   ESTILOIMAGEN		VARCHAR(32)          null,
   ESTADOMENU           VARCHAR(128)         null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TMENU primary key (IDMENU)
);

/*==============================================================*/
/* Table: TPROVEEDOR                                            */
/*==============================================================*/
create table TPROVEEDOR (
   IDPROVEEDOR          DECIMAL(9,0)         not null,
   NUMDOCPROVEEDOR      VARCHAR(13)          null,
   ESTADOPROVEEDOR      VARCHAR(4)           null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRIO       DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TPROVEEDOR primary key (IDPROVEEDOR)
);

/*==============================================================*/
/* Table: TPUNTOEMISION                                         */
/*==============================================================*/
create table TPUNTOEMISION (
   IDPUNTOEMISION       DECIMAL(9,0)         not null,
   NOMBREPUNTOEMISION   VARCHAR(128)         null,
   CODIGOPUNTOEMISION   VARCHAR(64)          null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   IDESTABLECIMIENTO    DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TPUNTOEMISION primary key (IDPUNTOEMISION)
);

/*==============================================================*/
/* Table: TTIPOCOMPROBANTE                                      */
/*==============================================================*/
create table TTIPOCOMPROBANTE (
   IDTIPOCOMPROBANTE    DECIMAL(9,0)         not null,
   CODIGOCOMPROBANTE    DECIMAL(2,0)         null,
   NOMBRECOMPROBANTE    VARCHAR(64)          null,
   ESTADOCOMPROBANTE    VARCHAR(4)           null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TTIPOCOMPROBANTE primary key (IDTIPOCOMPROBANTE)
);

/*==============================================================*/
/* Table: TTIPODEDUCIBLE                                        */
/*==============================================================*/
create table TTIPODEDUCIBLE (
   IDTIPODEDUCIBLE      DECIMAL(9,0)         not null,
   NOMBREDEDUCIBLE      VARCHAR(64)          null,
   ESTADODEDUCIBLE      VARCHAR(64)          null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TTIPODEDUCIBLE primary key (IDTIPODEDUCIBLE)
);

/*==============================================================*/
/* Table: TTIPODOCUMENTO                                        */
/*==============================================================*/
create table TTIPODOCUMENTO (
   IDTIPODOCUMENTO      DECIMAL(9,0)         not null,
   TIPODOCUMENTO        VARCHAR(64)          null,
   DESCRIPCION          VARCHAR(64)          null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TTIPODOCUMENTO primary key (IDTIPODOCUMENTO)
);

/*==============================================================*/
/* Table: TUSUARIO                                              */
/*==============================================================*/
create table TUSUARIO (
   IDUSUARIO            DECIMAL(9,0)         not null,
   CUENTAUSUARIO        VARCHAR(64)          null,
   NOMBREUSUARIO        VARCHAR(128)         null,
   CLAVEUSUARIO         VARCHAR(128)         null,
   ESTADOUSUARIO        VARCHAR(128)         null,
   TIPOUSUARIO          VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   USUARIOREGISTRO      VARCHAR(64)          null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   constraint PK_TUSUARIO primary key (IDUSUARIO)
);

/*==============================================================*/
/* Table: TUSUARIOMENU                                          */
/*==============================================================*/
create table TUSUARIOMENU (
   IDUSUARIO            DECIMAL(9,0)         not null,
   IDMENU               DECIMAL(9,0)         not null,
   ESTADOUSUMEN         VARCHAR(128)         null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TUSUARIOMENU primary key (IDUSUARIO, IDMENU)
);

/*==============================================================*/
/* Table: TXMLCOMPROBANTE                                       */
/*==============================================================*/
create table TXMLCOMPROBANTE (
   IDXMLCOMPROBANTE     DECIMAL(9,0)         not null,
   IDCOMPROBANTE        DECIMAL(9,0)         null,
   DOCUMENTOXML         CHAR(2048)           null,
   USUARIOREGISTRO      DECIMAL(9,0)         null,
   USUARIOMODIFICACION  VARCHAR(64)          null,
   FECHAREGISTRO        DATE                 null,
   FECHAMODIFICACION    DATE                 null,
   constraint PK_TXMLCOMPROBANTE primary key (IDXMLCOMPROBANTE)
);

alter table TAUTCOMPROBANTE
   add constraint TUsuPKTAutCom foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TAUTCOMPROBANTE
   add constraint TComPKTAutCom foreign key (IDCOMPROBANTE)
      references TCOMPROBANTE (IDCOMPROBANTE)
      on delete restrict on update restrict;

alter table TAUTCOMPROBANTE
   add constraint TErrAutSriPKTAutCom foreign key (IDERRORSRI)
      references TERRORESAUTORIZACIONSRI (IDERRORSRI)
      on delete restrict on update restrict;

alter table TAUTCOMPROBANTE
   add constraint TClaConPKTAutCom foreign key (IDCLAVESCONTINGENCIA)
      references TCLAVESCONTINGENCIA (IDCLAVESCONTINGENCIA)
      on delete restrict on update restrict;

alter table TAUTCOMPROBANTE
   add constraint TFirElePKTAutCom foreign key (IDFIRMAELECTRONICA)
      references TFIRMAELECTRONICA (IDFIRMAELECTRONICA)
      on delete restrict on update restrict;

alter table TCATALOGOARTICULOS
   add constraint TUsuPKTCatArt foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TCLAVESCONTINGENCIA
   add constraint TUsuPKTClaCon foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TCLIENTE
   add constraint TUsuPKTCli foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TCLIENTE
   add constraint TTipDocPKTCli foreign key (IDTIPODOCUMENTO)
      references TTIPODOCUMENTO (IDTIPODOCUMENTO)
      on delete restrict on update restrict;

alter table TCOMPROBANTE
   add constraint TCliPKTCom foreign key (IDCLIENTE)
      references TCLIENTE (IDCLIENTE)
      on delete restrict on update restrict;

alter table TCOMPROBANTE
   add constraint TUsuPKTCom foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TCOMPROBANTE
   add constraint TTipComPKTCom foreign key (IDTIPOCOMPROBANTE)
      references TTIPOCOMPROBANTE (IDTIPOCOMPROBANTE)
      on delete restrict on update restrict;

alter table TDEDUCIBLEFACTURA
   add constraint TTipDedPKTDedFac foreign key (IDTIPODEDUCIBLE)
      references TTIPODEDUCIBLE (IDTIPODEDUCIBLE)
      on delete restrict on update restrict;

alter table TDEDUCIBLEFACTURA
   add constraint TFacCliExtPKTDedFac foreign key (IDFACTCLIENTEEXT)
      references TFACTURACLIENTEEXTERNO (IDFACTCLIENTEEXT)
      on delete restrict on update restrict;

alter table TDEDUCIBLEFACTURA
   add constraint TUsuPKTDedFac foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TDEDUCIBLES
   add constraint TProPKTDed foreign key (IDPROVEEDOR)
      references TPROVEEDOR (IDPROVEEDOR)
      on delete restrict on update restrict;

alter table TDEDUCIBLES
   add constraint TTipDedPKTDed foreign key (IDTIPODEDUCIBLE)
      references TTIPODEDUCIBLE (IDTIPODEDUCIBLE)
      on delete restrict on update restrict;

alter table TDEDUCIBLES
   add constraint TCliPKTDed foreign key (IDCLIENTE)
      references TCLIENTE (IDCLIENTE)
      on delete restrict on update restrict;

alter table TDEDUCIBLES
   add constraint TUsuPKTDed foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TDETALLECOMPROBANTE
   add constraint TUsuPKTDetCom foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TDETALLECOMPROBANTE
   add constraint TComPKTDetCom foreign key (IDCOMPROBANTE)
      references TCOMPROBANTE (IDCOMPROBANTE)
      on delete restrict on update restrict;

alter table TDETALLECOMPROBANTE
   add constraint TCatArtPKTDetCom foreign key (IDARTICULO)
      references TCATALOGOARTICULOS (IDARTICULO)
      on delete restrict on update restrict;

alter table TDETALLEFACTEXT
   add constraint TUsuPKTDetFacExt foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TDETALLEFACTEXT
   add constraint TFacCliExtPKTDetFacExt foreign key (IDFACTCLIENTEEXT)
      references TFACTURACLIENTEEXTERNO (IDFACTCLIENTEEXT)
      on delete restrict on update restrict;

alter table TDETALLEFACTEXT
   add constraint TTipDedPKTDetFacExt foreign key (IDTIPODEDUCIBLE)
      references TTIPODEDUCIBLE (IDTIPODEDUCIBLE)
      on delete restrict on update restrict;

alter table TESTABLECIMIENTO
   add constraint TUsuPKTEst foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TFACTURACLIENTEEXTERNO
   add constraint TUsuPKTFacCliExt foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TFACTURACLIENTEEXTERNO
   add constraint TProPKTFacCliExt foreign key (IDPROVEEDOR)
      references TPROVEEDOR (IDPROVEEDOR)
      on delete restrict on update restrict;

alter table TFACTURACLIENTEEXTERNO
   add constraint TCliPKTFacCliExt foreign key (IDCLIENTE)
      references TCLIENTE (IDCLIENTE)
      on delete restrict on update restrict;

alter table TFIRMAELECTRONICA
   add constraint TUsuPKTFirEle foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TMENU
   add constraint TUsuPKTMen foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TPROVEEDOR
   add constraint TUsuPKTPro foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TPUNTOEMISION
   add constraint TEstPKTPunEmi foreign key (IDESTABLECIMIENTO)
      references TESTABLECIMIENTO (IDESTABLECIMIENTO)
      on delete restrict on update restrict;

alter table TPUNTOEMISION
   add constraint TUsuPKTPunEmi foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TTIPOCOMPROBANTE
   add constraint TUsuPKTTipCom foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TTIPODEDUCIBLE
   add constraint TUsuPKTTipDed foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TTIPODOCUMENTO
   add constraint TUsuPKTTipDoc foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TUSUARIOMENU
   add constraint TUsuPKTUsuMen foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

alter table TUSUARIOMENU
   add constraint TMenPKTUsuMen foreign key (IDMENU)
      references TMENU (IDMENU)
      on delete restrict on update restrict;

alter table TXMLCOMPROBANTE
   add constraint TComPKTXmlCom foreign key (IDCOMPROBANTE)
      references TCOMPROBANTE (IDCOMPROBANTE)
      on delete restrict on update restrict;

alter table TXMLCOMPROBANTE
   add constraint TUsuPKTXmlCom foreign key (USUARIOREGISTRO)
      references TUSUARIO (IDUSUARIO)
      on delete restrict on update restrict;

