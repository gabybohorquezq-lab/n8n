SELECT distinct ip.ITEM AS "SKU",
                NVL((CASE
                      WHEN (SELECT (IA.PVP_CON_IVA * ITS.UNIDAD)
                              FROM PR_ITEMS_AUTORIZADOS IA
                             INNER JOIN PR_RELACION_DIGITAL RD
                                ON IA.ITEM = RD.ITEM
                             INNER JOIN FA_INT_ITEM_SUELTAS ITS
                                ON ITS.ITEM_SUELTA = IA.ITEM
                             INNER JOIN PR_ITEMS ITE
                                ON ITE.CODIGO = IA.ITEM
                             INNER JOIN PR_ITEMS_PARAMETROS ITP
                                ON ITP.ITEM = ITE.CODIGO
                             WHERE FARMACIA = 15
                               AND ITP.DISCONTINUADO = 'A'
                               AND IA.ACTIVO = 'S'
                               AND IA.ITEM = ip.ITEM) > 0 THEN
                       (SELECT TO_CHAR(IA.PVP_CON_IVA * ITS.UNIDAD,
                                       'FM99999999999999990D00',
                                       'NLS_NUMERIC_CHARACTERS=''.,''')
                          FROM PR_ITEMS_AUTORIZADOS IA
                         INNER JOIN FA_INT_ITEM_SUELTAS ITS
                            ON ITS.ITEM_SUELTA = IA.ITEM
                         WHERE FARMACIA = 15
                           AND IA.ITEM = ip.ITEM)
                    
                      WHEN (SELECT IA.PVP_CON_IVA
                              FROM PR_ITEMS_AUTORIZADOS IA
                             WHERE IA.FARMACIA = 1115
                               AND IA.ITEM =
                                   (SELECT fiis.ITEM_SUELTA
                                      FROM FARMACIAS.FA_INT_ITEM_SUELTAS fiis
                                     WHERE fiis.ITEM_CAJA = ip.ITEM)) > 0 THEN
                       (SELECT TO_CHAR(IA.PVP_CON_IVA,
                                       'FM99999999999999990D00',
                                       'NLS_NUMERIC_CHARACTERS = ''.,''')
                          FROM PR_ITEMS_AUTORIZADOS IA
                         WHERE IA.FARMACIA = 15
                           AND IA.ITEM =
                               (SELECT ITEM_CAJA
                                  FROM FARMACIAS.FA_INT_ITEM_SUELTAS fiis
                                 WHERE fiis.ITEM_CAJA = ip.ITEM))
                    
                      ELSE
                       (SELECT TO_CHAR(IA.PVP_CON_IVA,
                                       'FM99999999999999990D00',
                                       'NLS_NUMERIC_CHARACTERS = ''.,''')
                          FROM PR_ITEMS_AUTORIZADOS IA
                         WHERE IA.FARMACIA = 15
                           AND IA.ITEM = ip.ITEM)
                    END),
                    0) AS "PRICE",
                (CASE
                  WHEN MAX(NVL(pr.UNIDADES, 0)) >
                       (select to_number(det.valor_parametro)
                          from INTEGRACIONES.INT_PARAMETROS_POS     cab,
                               integraciones.int_parametros_detalle det
                         where cab.nombre_proceso = 'PEDIDOSYA'
                           and cab.codigo = det.codigo_parametro) THEN
                   1
                  ELSE
                   0
                END) AS "ACTIVE"
  FROM INTEGRACIONES.INT_PEDIDOSYA ip
  LEFT JOIN (SELECT NVL(tb.PACK_NO, ss.PRO_CODIGO_INTERNO) AS PRO_CODIGO_INTERNO,
                    CASE
                      WHEN tb.PACK_NO IS NOT NULL THEN
                       TRUNC(ss.UNIDADES / tb.PACK_QTY)
                      ELSE
                       ss.UNIDADES
                    END AS STOCK,
                    NVL(tb.PACK_QTY, 1) AS UNIDAD,
                    ss.UNIDADES,
                    ss.LOC_LOCAL
               FROM trx3.scfr_stock_en_linea@to_appdfm.uio ss
               LEFT JOIN (SELECT PACK_NO, ITEM, PACK_QTY
                           FROM rms.packitem@prod_to_momprd.uio) tb
                 ON ss.PRO_CODIGO_INTERNO = tb.ITEM
              WHERE ss.LOC_LOCAL =15 ) pr
    ON pr.PRO_CODIGO_INTERNO = ip.ITEM
    WHERE ip.ITEM = '100148981'
 group by ip.ITEM;

