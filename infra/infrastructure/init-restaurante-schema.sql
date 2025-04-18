DO $$
BEGIN
  IF NOT EXISTS(
    SELECT schema_name
    FROM information_schema.schemata
    WHERE schema_name = 'restaurante_schema'
  ) THEN
      EXECUTE 'CREATE SCHEMA restaurante_schema';
  END IF;
END 
$$;