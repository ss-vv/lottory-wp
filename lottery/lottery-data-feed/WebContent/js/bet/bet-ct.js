Jet().$package(function(ctzc){
    /**
     * 组合算法
     *     ms: 投注场次数
     *      m: 过关方式m@n中的m，m<=ms
     * caller: PassTypeCaller
     */
    function combinate(ms, m, caller){
        var i = 0, MAX = m - 1, max = ms - MAX, c = [0, 0, 0, 0, 0, 0, 0, 0];        
        while(c[0] < max){
            for(i++; i < m; i++){
                c[i] = c[i - 1] + 1;
            }
            
            while(c[MAX] < ms){
            	//当当前组合存在胆码或不需要胆码时方可计算注数和钱数
            	if(caller.haveSeed(c)){
            		caller.call(c);
            	}
                c[MAX]++;
            }
            
            i = MAX - 1;
            while(i >= 0){
                if (c[i] + 1 < c[i + 1]) {
                    c[i]++;
                    break;
                }
                i--;
            }
        }
    }
    // 过关方式
    function mn(passType,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o){
        switch(passType){
            case 8001:
                return a*b*c*d*e*f*g*h;
            case 9001:
                return a*b*c*d*e*f*g*h*i;
            case 12001:
                return a*b*c*d*e*f*g*h*i*j*k*l;
            case 14001:
                return a*b*c*d*e*f*g*h*i*j*k*l*m*n;
            default:
                return 0;
        }
    }
    
    function c(pt, val, cur){
        var v = function(i){
            return (pt > (i+1) * 1000 ? val[cur[i]] : 0);
        };
        return mn(pt,v(0),v(1),v(2),v(3),v(4),v(5),v(6),v(7),v(8),v(9),v(10),v(11),v(12),v(13),v(14));    
    }
    
    function fill(len, v){
        var a = [];
        for(var i = 0; i < len; i++){
            a[i] = (v ? i : 0);
        }
        return a;
    }
    
    var BetStrategy = function(){};
    BetStrategy.prototype = {
        match: function(playId){
            return (playId.indexOf(this.name) == 0);
        },

        resolve: function(s){
            throw new Error('不支持该玩法');
        }
    };
    BetStrategy = Jooe.extend(BetStrategy.prototype);

    
    var PassCaller = function(){};
    PassCaller.prototype = {
        init: function(option){
            this.bet = option.bet;
            this.mn = 0;
            this.seed = option.bet.seed;//已选胆码
        },
        
        reset: function(mn){
            this.mn = mn;
        },
        call: function(cur){
            var note = c(this.mn, this.bet.opts, cur);
            var maxBonus = c(this.mn, this.bet.maxOdds, cur);
      
            this.bet.note += note;
            this.bet.maxBonus += maxBonus;
        },
        haveSeed:function(cur){
        	//是否有胆码
        	var haveSeed = true;
        	//如果有胆，则判断当前投注是否包含胆码
        	if(this.seed.length>0){
            	var curSet = new Set();
            	for(var i=0;i<this.mn.toString().substring(0, 1);i++){
            		curSet.add(cur[i]);
            	}
            	for(var i=0;i<this.seed.length;i++){
            		if(!(this.seed[i] in curSet)){
            			haveSeed = false;
            		}
            	}
        	}
        	return haveSeed;
        }
        
    };
    PassCaller = Jooe.extend(PassCaller.prototype);
   
    
    var JCBetStrategy = function(){};
    JCBetStrategy.prototype = {
        resolve: function(s){
            var bet = this._createBet(s.matchs);
            var passCaller = new PassCaller({bet: bet});
            
            for(var i = 0; i < s.passTypes.length; i++){
                this._resolve(bet, passCaller, s.passTypes[i]);                
            }
            
            bet.note *= s.multiple;
            bet.maxBonus *= s.multiple;
            
//            if(s.playId == '02_ZC_1' || s.playId == '08_LC_1' || s.playId.charAt(s.playId.length - 1) == '2'){
//                // 竞彩足球比分单关,篮球胜分差单关,过关
//                bet.maxBonus *= 2;
//            }
            
            return bet;
        },
        
        _resolve: function(bet, passCaller, passType){
            var mn = this._parsePassType(passType);
            if(mn[0] > bet.opts.length){
                throw new Error('所选场次不支持过关方式：' + passType);
            }
            
            passCaller.reset(mn[0] * 1000 + mn[1]);
            combinate(bet.opts.length, mn[0], passCaller);
        },
        
        _createBet: function(matchs){
            var bet = {
                note: 0,
                maxBonus: 0,
                maxOdds: [],
                opts: [],
                seed:[]//胆码
            };
            for(var i = 0; i < matchs.length; i++){
                var odds = matchs[i].odds;
                bet.opts[i] = odds.length;
                bet.maxOdds[i] = parseFloat(odds[0]);
                //记录胆码id
                if(matchs[i].seed){
                	bet.seed.push(i);
                }
                
                for(var j = 1, k = odds.length; j < k; j++){
                    bet.maxOdds[i] = Math.max(bet.maxOdds[i], parseFloat(odds[j]));
                }
            }
            return bet;
        },
        
        _parsePassType: function(passType){
            var mn = passType.split('@');
            if(mn.length != 2){
                throw new Error('无效过关方式：' + passType);
            }
            return [parseInt(mn[0]), parseInt(mn[1])];
        }
    };
    JCBetStrategy = BetStrategy.extend(JCBetStrategy.prototype);
    
    
    var Bet = function(){};
    Bet.prototype = {
        init: function(option){
            this.multi = 1;
            this.pass = [1]; // 已选过关方式
            this.passTypes = [option.passTypes]; // 有效过关方式
            this.matchs = [];    // 受注赛事
            this.mc = 0;         // 受注场次
            this.seedIndexs = []; // 胆码下标集合
            this.seeds = 0; // 胆码数
            this.minBetMc = option.minBetMc;   // 最小有效投注赛事数量
            this.maxMc = option.maxMc;      // 最大可选赛事数量
            this.mask = Math.pow(2, option.renderer.optionCount) - 1;
            this.money = 0;
            this.strategy = option.strategy || new JCBetStrategy(); // 投注数据计算策略
            this.ui = option.ui;
            this.playId = option.playId;
        },
        
        /**
         * 将JSON数据对象解析为赛事对象
         * @param d
         * @returns 
         */
        parseMatch: function(d){
            return {
                id: d.id,
                hName: d.homeTeamName,
                gName: d.guestTeamName,
                opts: d.options.split(','),
                odds: d.odds.split(','),
                score: d.defaultScore,
                pOdds: d.priorOdds,
                lName: d.leagueName,
                pTime: d.playingTime,
                deadline:d.entrustDeadline,
                code: d.code,
                cnCode: d.matchId,
                seed: false,
                sel: 0,
                issueNumber:d.issueNumber,
                color:d.color==null?'#393':d.color
            };
        },
        
        togglePass: function(i){
            if(this.pass[i] == 1){
                this.pass[i] = 0;
            }else{
                this.pass[i] = 1;
            }
        },
        
        parseMatchs: function(data){
            var ctx = this, ms = [];
            $.each(data, function(i, d){
                ms[i] = ctx.parseMatch(d);
            });
            this.matchs = ms;
            return ms;
        },
        
        parseMatchsCopy: function(data){
        	var ctx = this, ms = [];
        	$.each(data, function(i, d){
        		ms.push(ctx.parseMatch(d));
        		ms.push(ctx.parseMatch(d));
        	});
        	this.matchs = ms;
        	return ms;
        },
        
        _getBetMatchs: function(){
            var bms = [], ms = this.matchs, m, sel, odds, opts;
            for(var i = 0, len = ms.length; i < len; i++){
                if(ms[i].sel == 0){
                    continue;
                }
                
                m = ms[i];
                sel = m.sel;
                seed = m.seed;//胆码
                odds = [];
                opts = [];
                for(var j = 0, k = m.opts.length; j < k; j++){
                    if(sel & Math.pow(2, j)){
                        opts.push(m.opts[j]);
                        odds.push(m.odds[j]);
                    }
                }
                bms.push({
                    id: m.id,
                    code: m.code,
                    odds: odds,
                    opts: opts,
                    seed: seed,
                    issueNumber:m.issueNumber
                });
            }
            
            return bms;
        },
        
        toBetMatchs: function(){
            var bms = this._getBetMatchs(), r = [];
            var s =bms[0].issueNumber + ';';
            var options = [];
            var seed = [];var length = bms.length;
            var playId = bms[0].id;
            
            if(playId.indexOf('ZC_14') != -1 || playId.indexOf('ZC_R9') != -1){
            	options = ['-','-','-','-','-','-','-','-','-','-','-','-','-','-'];
            }else if(playId.indexOf('ZC_BQ') != -1){
            	options = ['-','-','-','-','-','-','-','-','-','-','-','-'];
            }else{
            	options = ['-','-','-','-','-','-','-','-'];
            }
            for(var i = 0; i < length; i++){
            	if(playId.indexOf('ZC_14') != -1 || playId.indexOf('ZC_R9') != -1){
            		options[bms[i].code - 1] = bms[i].opts.join('');
            	}else{
            		options[i] = bms[i].opts.join('');
            	}
                if(bms[i].seed == true){
                	seed.push(bms[i].code-1); 
                }
            }
            r.push(s + options.join(",") +  ';' + seed.join(","));
            return r.join('|');
        },
        
        // 计算投注金额和最高奖金
        resolve: function(){
            var m = 0, b = 0;n=0;
            if(this.pass.length > 0){
                var bms = this._getBetMatchs();
                if(bms.length >= this.minBetMc){
                    var r = this.strategy.resolve({
                        matchs: bms,
                        passTypes: this.getBetPass(),
                        multiple: this.multi,
                        playId: this.playId
                    });
                    
                    m = r.note * 2;
                    b = Math.round(r.maxBonus * 100)/100;
                    n = r.note;
                }
            }
            this.money = m;
            return {money: m, bonus: b,seeds:this.seeds,note:n};
        },
        
        /**
         * 得到胆码集合
         * @author Wang Lei
         */
        getSeedSet:function(){
        	var seedset = new Set();
    		for(var m=0;m<this.matchs.length;m++){
    			if(this.matchs[m].seed){
    				seedset.add(m);
    			}
    		}
    		return seedset;
        },
        
        /**
         * 计算胆码数
         * @returns {Number}
         */
        countSeeds:function(){
        	var count = 0;
    		for(var m=0;m<this.matchs.length;m++){
    			if(this.matchs[m].seed){
    				count++;
    			}
    		}
    		this.seeds=count;
    		return count;
        },
        
        /**
         * 更新赛事胆码
         * @author Wang Lei
         * @param i 赛事序号集合
         */
        updateSeed:function(i){
        	this.seedIndexs = i;
    		var seedset = new Set();
    		for(var m=0;m<i.length;m++){
    			seedset.add(i[m]);
    		}
    		for(var n=0;n<this.matchs.length;n++){
        		if(n in seedset){
        			this.matchs[n].seed=true;
        		}else{
        			this.matchs[n].seed=false;
        		}
        	}
    		this._trimPassType();
        },
        
        /**
         * 添加或删除赛事
         * @param i 赛事序号
         * @param j 投注选项的序号
         */
        clickOption: function(i, j){
            var sel = this.matchs[i].sel, old = sel, v = Math.pow(2, j);
            if(sel & v){
                sel &= (this.mask - v);
            }else{
                sel |= v;
            }
            
            if(old > 0 && sel == 0){
                this.mc--;
                this.matchs[i].seed = false;//同时去掉胆码
                this._trimPassType();
            }else if(old == 0 && sel > 0){
                if((this.mc + 1) >= this.minBetMc){
//                    alert('最多允许投注' + this.maxMc + '场');
                	this.pass = [1];
                }
                this.mc++;
            }
            
            this.matchs[i].sel = sel;
        },
        
        addMatch: function(i){
            if(this.matchs[i].sel == 0){
                this.mc++;
            }
            this.matchs[i].sel = this.mask;
        },
        
        removeMatch: function(i){
            if(this.matchs[i].sel > 0){
                this.matchs[i].sel = 0;
                //取消胆码
                this.matchs[i].seed = false;
                this.mc--;
                this._trimPassType();
            }
        },
        
        clearMatch: function(){
            var ms = this.matchs;
            for(var i = 0, len = ms.length; i < len; i++){
                ms[i].sel = 0;
                //取消胆码
                ms[i].seed = false;
            }
            this.mc = 0;
            this.pass = [0];
        },
        
        getBetPass: function(){
            var pt = [], pts = this.passTypes;
            for(var i = 0, len = pts.length; i < len; i++){
                if(this.pass[i] == 1){
                    pt.push(pts[i]);
                }
            }
            return pt;
        },
        
        _trimPassType: function(){
        	this.countSeeds();
            if(this.mc < this.minBetMc){
                if(this.minBetMc == 1){
                    this.pass = [1];
                }else{
                    this.pass = [1];
                }
            }else{
                var v = this.pass, pts = this.passTypes, mc = this.mc; var seedcount = this.seedIndexs.length;
                $.each(this.passTypes, function(i, p){
                	//去掉不支持玩法(玩法mn的m不能大于已选赛事数，胆码数不能大于玩法mn的m)
                    if(mc < parseInt(p.substring(0, p.indexOf('@'))) || seedcount > parseInt(p.substring(0, p.indexOf('@')))){
                        v[i] = 0;
                    }
                });
                this.pass = v;
            }
        }
    };
    Bet = Jooe.extend(Bet.prototype);
    
    var Set = function() {};
    Set.prototype = {
    		init:function(){
    			this.all = [];
    		}
    };
    Set.prototype.add = function(o) {
    		if(!this[o]){
    			this.all.push(o);
    		}
    		this[o] = true;
    	};
    Set.prototype.remove = function(o) { 
    		delete this[o]; 
    	};
    Set.prototype.setAll = function(o) {
    		this.all = o;
    	};
    Set = Jooe.extend(Set.prototype);
    	
    this.JCBetStrategy = JCBetStrategy;
    this.Bet = Bet;
    this.Set = Set;
    

});