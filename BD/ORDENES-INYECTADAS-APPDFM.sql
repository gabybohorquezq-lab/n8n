select * from trx3.scfr_mov_documentos_txs d,
trx3.scfr_mov_transacciones t
where d.txo_codigo_tx = t.codigo_tx and t.loc_local = d.loc_local
and t.emp_empresa = d.emp_empresa
--and t.grabado = '2/11/2024'
--and d.txo_codigo_tx = 253843287
--and d.txo_correlativo_tx= 2024091071918
--and d.Tmo_Movimiento = 'VENFAC'
and trim(d.pin_ot_recetario) 
in ('FCC10526199')
