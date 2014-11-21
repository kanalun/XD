package com.xd.tools.db.meta;

/**
 * 数据库 KEY元数据
 * 
 * @author QCC
 * 
 */
public abstract class KeyMeta implements DatabaseMeta
{
	public String PKTABLE_CAT;// String => primary key table catalog (may be
								// null)
	public String PKTABLE_SCHEM;// String => primary key table schema (may be
								// null)
	public String PKTABLE_NAME;// String => primary key table name
	public String PKCOLUMN_NAME;// String => primary key column name
	public String FKTABLE_CAT;// String => foreign key table catalog (may be
								// null)
	// being exported (may be null)
	public String FKTABLE_SCHEM;// String => foreign key table schema (may be
								// null)
	// being exported (may be null)
	public String FKTABLE_NAME;// String => foreign key table name being
								// exported
	public String FKCOLUMN_NAME;// String => foreign key column name being
								// exported
	public short KEY_SEQ;// short => sequence number within foreign key( a value
							// of
	// 1 represents the first column of the foreign key, a
	// value of 2 would represent the second column within
	// the foreign key).
	public short UPDATE_RULE;// short => What happens to foreign key when
								// primary
	// is updated:
	// importedNoAction - do not allow update of primary key if it has been
	// imported
	// importedKeyCascade - change imported key to agree with primary key
	// update
	// importedKeySetNull - change imported key to NULL if its primary key
	// has been updated
	// importedKeySetDefault - change imported key to default values if its
	// primary key has been updated
	// importedKeyRestrict - same as importedKeyNoAction (for ODBC 2.x
	// compatibility)
	public short DELETE_RULE;// short => What happens to the foreign key when
	// primary is deleted.
	// importedKeyNoAction - do not allow delete of primary key if it has
	// been imported
	// importedKeyCascade - delete rows that import a deleted key
	// importedKeySetNull - change imported key to NULL if its primary key
	// has been deleted
	// importedKeyRestrict - same as importedKeyNoAction (for ODBC 2.x
	// compatibility)
	// importedKeySetDefault - change imported key to default if its primary
	// key has been deleted
	public String FK_NAME;// String => foreign key name (may be null)
	public String PK_NAME;// String => primary key name (may be null)
	public short shortDEFERRABILITY;// short => can the evaluation of foreign
									// key

	// constraints be deferred until commit

	// importedKeyInitiallyDeferred - see SQL92 for definition
	// importedKeyInitiallyImmediate - see SQL92 for definition
	// importedKeyNotDeferrable - see SQL92 for definition
	// }

	public KeyMeta(String FK_NAME)
	{
		this.FK_NAME = FK_NAME;
	}
}