-- Apply after the Hibernate-managed movie table exists.
-- Re-running this script replaces the functions without changing movie data.
BEGIN;

CREATE OR REPLACE FUNCTION lab_delete_one_movie_by_genre(p_genre text)
RETURNS integer
LANGUAGE plpgsql
AS $$
DECLARE
    deleted_id integer;
BEGIN
    DELETE FROM movie
    WHERE id = (
        SELECT id FROM movie WHERE genre = p_genre ORDER BY id LIMIT 1
    )
    RETURNING id INTO deleted_id;

    RETURN deleted_id;
END;
$$;

CREATE OR REPLACE FUNCTION lab_sum_golden_palms()
RETURNS bigint
LANGUAGE sql
AS $$
    SELECT COALESCE(SUM(golden_palm_count), 0)::bigint FROM movie;
$$;

CREATE OR REPLACE FUNCTION lab_count_movies_genre_before(p_genre text)
RETURNS bigint
LANGUAGE sql
AS $$
    SELECT COUNT(*) FROM movie WHERE genre < p_genre;
$$;

CREATE OR REPLACE FUNCTION lab_movies_without_oscars()
RETURNS SETOF movie
LANGUAGE sql
AS $$
    SELECT * FROM movie WHERE oscars_count = 0 ORDER BY id;
$$;

CREATE OR REPLACE FUNCTION lab_add_oscar_to_r_movies()
RETURNS integer
LANGUAGE plpgsql
AS $$
DECLARE
    updated_count integer;
BEGIN
    UPDATE movie SET oscars_count = oscars_count + 1 WHERE mpaa_rating = 'R';
    GET DIAGNOSTICS updated_count = ROW_COUNT;
    RETURN updated_count;
END;
$$;

COMMIT;
