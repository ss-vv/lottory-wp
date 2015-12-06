Jet().$package(function(J){
    
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
                caller.call(c);
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
            case 1001:
                return a;
            case 2001:
                return a*b;
            case 2003:
                return (a+1)*(b+1)-1;
            case 3001:
                return a*b*c;
            case 3003:
                return a*(b+c)+b*c;
            case 3004:
                return (a+1)*b*c+a*(b+c);
            case 3007:
                return (a+1)*(b+1)*(c+1)-1;
            case 4001:
                return a*b*c*d;
            case 4004:
                return a*b*(c+d)+(a+b)*(c*d);
            case 4005:
                return ((a+1)*(b+1)-1)*(c+1)*(d+1)-(a*b+(a+b)*(c+d+1));
            case 4006:
                return a*b+(a+b)*(c+d)+c*d;
            case 4011:
                return ((a+1)*(b+1)*(c+1)-1)*(d+1)-(a+b+c);
            case 4015:
                return (a+1)*(b+1)*(c+1)*(d+1)-1;
            case 5001:
                return a*b*c*d*e;
            case 5005:
                return a*b*c*(d+e)+(a*(b+c)+b*c)*d*e;
            case 5006:
                return a*b*c*(d+e+d*e)+(a*b+a*c+b*c)*d*e;
            case 5010:
                return a*(b+c+d+e)+b*(c+d+e)+c*(d+e)+d*e;
            case 5016:
                return ((a+1)*(b+1)*(c+1)-1)*(d+1)*(e+1)-(a*b+(a+b)*(c+d+e+1)+c*(d+e+1));
            case 5020:
                return (a+1)*b*(c+d+e)+(a+b+1)*c*(d+e)+(a+b+c+1)*d*e+a*(b+c+d+e);
            case 5026:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)-(a+b+c+d+e+1);
            case 5031:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)-1;
            case 6001:
                return a*b*c*d*e*f;
            case 6006:
                return a*b*c*(d*e+d*f+e*f)+(a*b+a*c+b*c)*d*e*f;
            case 6007:
                return a*b*c*(d*e+d*f+e*f+d*e*f)+(a*b+a*c+b*c)*d*e*f;
            case 6015:
                return a*b+(a+b)*(c+d+e+f)+c*d+(c+d)*(e+f)+e*f;
            case 6020:
                return a*b*(c+d+e+f)+(a+b)*c*(d+e+f)+(a+b+c)*d*(e+f)+(a+b+c+d)*e*f;
            case 6022:
                return a*b*c*d*(e+1)*(f+1)+(a*b*(c+d)+(a+b)*c*d)*((e+1)*(f+1)-1)+(a*b+(a+b)*(c+d)+c*d)*e*f;
            case 6035:
                return (a+b)*(c+d+e+f)+c*d+a*b*(c+d+e+f+1)+(a+b)*c*(d+e+f)+((a+b+c+1)*d+c)*(e+f)+(a+b+c+d+1)*e*f;
            case 6042:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-(a*(b+c+d+e+f+1)+b*(c+d+e+f+1)+c*(d+e+f+1)+d*(e+f+1)+(e+1)*(f+1));
            case 6050:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-(a+b+c+d+e+f+1)-(a*b*(c*d*(e+f)+(c+d)*e*f)+(a+b+a*b)*c*d*e*f);
            case 6057:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-(a+b+c+d+e+f+1);
            case 6063:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-1;
            case 7001:
                return a*b*c*d*e*f*g;
            case 7007:
                return a*b*c*(d*(e*(f+g)+f*g)+e*f*g)+(a*b+a*c+b*c)*d*e*f*g;
            case 7008:
                return a*b*c*(d*(e*(f+g)+f*g)+e*f*g)+(a*b+a*c+b*c+a*b*c)*d*e*f*g;
            case 7021:
                return a*b*c*(d*(e+f+g)+e*(f+g)+f*g)+(a*(b+c)+b*c)*d*(e*f+e*g+f*g)+(a*(b+c+d)+b*(c+d)+c*d)*e*f*g;
            case 7035:
                return a*b*c*(d+e+f+g)+(a*b+a*c+b*c)*(d*(e+f+g)+e*(f+g)+f*g)+(a+b+c)*d*(e*(f+g)+f*g)+(a+b+c+d)*e*f*g;
            case 7120:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)*(g+1)-(a+b+c+d+e+f+g+1);
            case 8001:
                return a*b*c*d*e*f*g*h;
            case 8008:
                return a*b*c*d*(e*(f*(g+h)+g*h)+f*g*h)+(a*(b*(c+d)+c*d)+b*c*d)*e*f*g*h;
            case 8009:
                return a*b*c*d*(e*(f*(g+h)+g*h)+(e+1)*f*g*h)+(a*(b*(c+d)+c*d)+b*c*d)*e*f*g*h;
            case 8028:
                return (a*(b+c)+b*c)*d*(e*(f*(g+h)+g*h)+f*g*h)+(a*(b+c+d)+b*(c+d)+c*d)*e*f*g*h+a*b*c*(d*(e*(f+g+h)+f*(g+h)+g*h)+e*(f*(g+h)+g*h)+f*g*h);
            case 8056:
                return a*b*c*(d*(e+f+g+h)+e*(f+g+h)+f*(g+h)+g*h)+(a*(b+c)+b*c)*d*(e*(f+g+h)+f*(g+h)+g*h)+(a*(b+c+d)+b*(c+d)+c*d)*e*(f*(g+h)+g*h)+(a*(b+c+d+e)+b*(c+d+e)+c*(d+e)+d*e)*f*g*h;
            case 8070:
                return a*b*(c*(d+e+f+g+h)+d*(e+f+g+h)+e*(f+g+h)+f*(g+h)+g*h)+(a+b)*c*(d*(e+f+g+h)+e*(f+g+h)+f*(g+h)+g*h)+(a+b+c)*d*(e*(f+g+h)+f*(g+h)+g*h)+(a+b+c+d)*e*(f*(g+h)+g*h)+(a+b+c+d+e)*f*g*h;
            case 8247:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)*(g+1)*(h+1)-(a+b+c+d+e+f+g+h+1);
            case 9001:
                return a*b*c*d*e*f*g*h*i;
            case 10001:
                return a*b*c*d*e*f*g*h*i*j;
            case 11001:
                return a*b*c*d*e*f*g*h*i*j*k;
            case 12001:
                return a*b*c*d*e*f*g*h*i*j*k*l;
            case 13001:
                return a*b*c*d*e*f*g*h*i*j*k*l*m;
            case 14001:
                return a*b*c*d*e*f*g*h*i*j*k*l*m*n;
            case 15001:
                return a*b*c*d*e*f*g*h*i*j*k*l*m*n*o;
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
        },
        
        reset: function(mn){
            this.mn = mn;
        },
        call: function(cur){
            var note = c(this.mn, this.bet.opts, cur);
            var maxBonus = c(this.mn, this.bet.maxOdds, cur);
      
            this.bet.note += note;
            this.bet.maxBonus += maxBonus;
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
            bet.maxBonus *= s.multiple * 2;
            
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
                opts: []
            };
            for(var i = 0; i < matchs.length; i++){
                var odds = matchs[i].odds;
                bet.opts[i] = odds.length;
                bet.maxOdds[i] = parseFloat(odds[0]);
                
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
            this.pass = (option.minBetMc == 1 ? 1 : 0); // 已选过关方式
            this.passTypes = ['1@1']; // 有效过关方式
            this.matchs = [];    // 受注赛事
            this.mc = 0;         // 受注场次
            this.minBetMc = option.minBetMc;   // 最小有效投注赛事数量
            this.maxMc = option.maxMc;      // 最大可选赛事数量
            this.mask = Math.pow(2, option.renderer.optionCount) - 1;
            this.money = 0;
            this.strategy = option.strategy || new JCBetStrategy(); // 投注数据计算策略
            this.ui = option.ui;
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
                cnCode: d.cnCode,
                seed: false,
                sel: 0
            };
        },
        
        togglePass: function(i){
            var v = Math.pow(2, i);
            if(this.pass & v){
                this.pass &= ~v;
            }else{
                this.pass |= v;
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
        
        _getBetMatchs: function(){
            var bms = [], ms = this.matchs, m, sel, odds, opts;
            for(var i = 0, len = ms.length; i < len; i++){
                if(ms[i].sel == 0){
                    continue;
                }
                
                m = ms[i];
                sel = m.sel;
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
                    opts: opts
                });
            }
            
            return bms;
        },
        
        toBetMatchs: function(){
            var bms = this._getBetMatchs(), r = [];
            for(var i = 0, len = bms.length; i < len; i++){
                r.push(bms[i].id + '-' + bms[i].code + bms[i].opts.join(''));
            }
            return r.join(',');
        },
        
        // 计算投注金额和最高奖金
        resolve: function(){
            var m = 0, b = 0;
            if(this.pass > 0){
                var bms = this._getBetMatchs();
                if(bms.length >= this.minBetMc){
                    var r = this.strategy.resolve({
                        matchs: bms,
                        passTypes: this.getBetPass(),
                        multiple: this.multi
                    });
                    
                    m = r.note * 2;
                    b = Math.round(r.maxBonus * 100)/100;
                }
            }
            this.money = m;
            return {money: m, bonus: b};
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
                this._trimPassType();
            }else if(old == 0 && sel > 0){
                if(this.mc == this.maxMc){
                    alert('最多允许投注' + this.maxMc + '场');
                    return;
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
                this.mc--;
                this._trimPassType();
            }
        },
        
        clearMatch: function(){
            var ms = this.matchs;
            for(var i = 0, len = ms.length; i < len; i++){
                ms[i].sel = 0;
            }
            this.mc = 0;
            this.pass = 0;
        },
        
        getBetPass: function(){
            var pt = [], pts = this.passTypes;
            for(var i = 0, len = pts.length; i < len; i++){
                if(this.pass & Math.pow(2, i)){
                    pt.push(pts[i]);
                }
            }
            return pt;
        },
        
        _trimPassType: function(){
            if(this.mc < this.minBetMc){
                this.pass = (this.minBetMc == 1 ? 1 : 0);
            }else{
                var v = this.pass, pts = this.passTypes, mc = this.mc;
                $.each(this.passTypes, function(i, p){
                    if(mc < parseInt(p.substring(0, p.indexOf('@')))){
                        v &= ~Math.pow(2, i);
                    }
                });
                this.pass = v;
            }
        }
    };
    Bet = Jooe.extend(Bet.prototype);
    
    this.JCBetStrategy = JCBetStrategy;
    this.Bet = Bet;
});