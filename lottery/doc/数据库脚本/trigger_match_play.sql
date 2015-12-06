CREATE TRIGGER trigger_fb_match_play_update
BEFORE UPDATE ON fb_match_play
FOR EACH ROW
BEGIN
SET NEW.prior_odds = OLD.odds;
END;